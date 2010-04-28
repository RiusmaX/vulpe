package org.vulpe.controller.util;

import java.io.ByteArrayOutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;

import org.vulpe.common.cache.VulpeCacheHelper;

public class ReportUtil {
	/**
	 * Returns StrutsReportUtil instance
	 */
	public static ReportUtil getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(ReportUtil.class)) {
			cache.put(ReportUtil.class, new ReportUtil());
		}
		return cache.get(ReportUtil.class);
	}

	protected ReportUtil() {
		// default constructor
	}

	protected byte[] exportReportToBytes(final JasperPrint jasperPrint,
			final JRExporter exporter) throws JRException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.exportReport();
		return baos.toByteArray();
	}

}