/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vulpe.controller.helper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
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

	private static final Logger LOG = Logger.getLogger(VulpeJobSchedulerHelper.class);

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
			for (URL url : urlsWebInfLib) {
				final String jarName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1);
				if (!VulpeConfigHelper.isSecurityEnabled()
						&& jarName.contains(VulpeConstants.VULPE_SECURITY)) {
					continue;
				}
				if (jarName.contains(VulpeConstants.VULPE)
						|| jarName.contains(VulpeConstants.COMMONS)
						|| jarName.contains(VulpeConstants.CONTROLLER)) {
					urls.add(url);
				}
			}
			final URL[] urlsFrameworkApplication = new URL[urls.size()];
			int count = 0;
			for (URL url : urls) {
				urlsFrameworkApplication[count] = url;
				count++;
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
						final Class classicClass = Class.forName(jobClass);
						if (VulpeJob.class.isAssignableFrom(classicClass)) {
							final Class<? extends VulpeJob> clazz = (Class<? extends VulpeJob>) classicClass;
							jobScheduler(scheduler, clazz.newInstance());
						}
					} catch (Exception e) {
						LOG.error(e);
					}
				}
				scheduler.start();
			} catch (SchedulerException e) {
				LOG.error(e);
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
			JobDetail jobDetail = new JobDetail(job.getClass().getName(), "group1", job.getClass());
			CronTrigger trigger = new CronTrigger("trigger1", "group1", job.getClass().getName(),
					"group1", jobAnnotation.value());
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			LOG.error(e);
		}
	}

}