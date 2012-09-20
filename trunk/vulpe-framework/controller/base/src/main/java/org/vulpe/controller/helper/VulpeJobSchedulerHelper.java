/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * 
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.controller.helper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.scannotation.AnnotationDB;
import org.scannotation.WarUrlFinder;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.scheduler.VulpeJob;
import org.vulpe.commons.scheduler.annotations.Job;

/**
 * Utility class to control scheduled jobs.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
public final class VulpeJobSchedulerHelper {

	private static final Logger LOG = LoggerFactory.getLogger(VulpeJobSchedulerHelper.class);

	private static AnnotationDB annotationDB;

	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

	/**
	 * Load list of classes noted with @Job
	 * 
	 * @param servletContext
	 * @return
	 */
	private static Set<String> loadJobClasses(final ServletContext servletContext) {
		scannotation(servletContext);
		final Set<String> jobClasses = annotationDB.getAnnotationIndex().get(Job.class.getName());
		return jobClasses;
	}

	/**
	 * Scanning libs of application to find noted classes.
	 * 
	 * @param servletContext
	 * @return
	 * @return
	 */
	private static void scannotation(final ServletContext servletContext) {
		if (annotationDB == null) {
			final URL urlWebInfClasses = WarUrlFinder.findWebInfClassesPath(servletContext);
			final URL[] urlsWebInfLib = WarUrlFinder.findWebInfLibClasspaths(servletContext);
			final List<URL> urls = new ArrayList<URL>();
			for (final URL url : urlsWebInfLib) {
				final String jarName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1);
				if (!VulpeConfigHelper.isSecurityEnabled()
						&& jarName.contains(VulpeConstants.VULPE_SECURITY)) {
					continue;
				}
				if (jarName.contains(VulpeConstants.VULPE)
						|| jarName.contains(VulpeConstants.COMMONS)
						|| jarName.contains(VulpeConstants.CONTROLLER)
						|| jarName.contains(VulpeConfigHelper.getApplicationName())) {
					urls.add(url);
				}
			}
			final URL[] urlsFrameworkApplication = new URL[urls.size()];
			int count = 0;
			for (final URL url : urls) {
				urlsFrameworkApplication[count] = url;
				++count;
			}
			annotationDB = new AnnotationDB();
			try {
				if (urlWebInfClasses != null) {
					annotationDB.scanArchives(urlWebInfClasses);
				}
				if (urlsFrameworkApplication != null && urlsFrameworkApplication.length > 0) {
					annotationDB.scanArchives(urlsFrameworkApplication);
				}
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	/**
	 * Schedule all class with annotations (@Job).
	 * 
	 * @param servletContext
	 */
	@SuppressWarnings("unchecked")
	public static void schedulerAnnotedJobs(final ServletContext servletContext) {
		final Set<String> jobClasses = loadJobClasses(servletContext);
		if (jobClasses != null && !jobClasses.isEmpty()) {
			try {
				final Scheduler scheduler = schedulerFactory.getScheduler();
				for (String jobClass : jobClasses) {
					try {
						final Class<?> classicClass = Class.forName(jobClass);
						if (VulpeJob.class.isAssignableFrom(classicClass)) {
							final Class<? extends VulpeJob> clazz = (Class<? extends VulpeJob>) classicClass;
							jobScheduler(scheduler, clazz.newInstance());
						}
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
				}
				scheduler.start();
			} catch (SchedulerException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param job
	 */
	public static void jobScheduler(final Scheduler scheduler, final VulpeJob job) {
		final Job jobAnnotation = job.getClass().getAnnotation(Job.class);
		try {
			final JobDetailImpl jobDetail = new JobDetailImpl();
			jobDetail.setName(job.getClass().getName());
			jobDetail.setGroup(jobAnnotation.group());
			jobDetail.setJobClass(job.getClass());
			final String triggerName = StringUtils.isNotBlank(jobAnnotation.trigger()) ? jobAnnotation
					.trigger()
					: job.getClass().getSimpleName().concat("Trigger");
			final CronTriggerImpl trigger = new CronTriggerImpl();
			trigger.setName(triggerName);
			trigger.setGroup(jobAnnotation.group());
			trigger.setJobName(job.getClass().getName());
			trigger.setJobGroup(jobAnnotation.group());
			trigger.setCronExpression(jobAnnotation.value());
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
}