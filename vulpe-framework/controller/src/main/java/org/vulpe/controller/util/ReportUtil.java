package org.vulpe.controller.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.views.jasperreports.JasperReportConstants;
import org.vulpe.common.beans.DownloadInfo;
import org.vulpe.common.cache.VulpeCacheHelper;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeBaseEntity;

public class ReportUtil implements JasperReportConstants {
	/**
	 * Returns ReportUtil instance
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

	@SuppressWarnings("unchecked")
	public byte[] getJasperReport(final String fileName,
			final String[] subReports,
			final Collection<VulpeBaseEntity<?>> collection, final String format) {
		try {
			String fullFileName = fileName;
			if (ControllerUtil.getInstance().getServletContext() != null) {
				fullFileName = ControllerUtil.getInstance().getServletContext()
						.getRealPath(fileName);
			}
			final JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(fullFileName);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					collection);

			final Map parameters = new HashMap();
			parameters.put("BASEDIR", StringUtils.replace(fullFileName,
					StringUtils.replace(fileName, "/", File.separator), ""));
			if (subReports != null && subReports.length > 0) {
				int count = 0;
				for (String subReport : subReports) {
					parameters.put("SUBREPORT_".concat(String.valueOf(count)),
							ControllerUtil.getInstance().getServletContext()
									.getRealPath(subReport));
					count++;
				}
			}
			final JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters, dataSource);
			if (jasperPrint == null || jasperPrint.getPages().isEmpty()) {
				return null;
			}
			if (format.equals(ReportUtil.FORMAT_PDF)) {
				return JasperExportManager.exportReportToPdf(jasperPrint);
			}
			JRExporter exporter;
			if (format.equals(ReportUtil.FORMAT_CSV)) {
				exporter = new JRCsvExporter();
			} else if (format.equals(ReportUtil.FORMAT_HTML)) {
				exporter = new JRHtmlExporter();
			} else if (format.equals(ReportUtil.FORMAT_XLS)) {
				exporter = new JRXlsExporter();
			} else if (format.equals(ReportUtil.FORMAT_XML)) {
				exporter = new JRXmlExporter();
			} else if (format.equals(ReportUtil.FORMAT_RTF)) {
				exporter = new JRRtfExporter();
			} else {
				throw new VulpeSystemException("vulpe.error.report");
			}
			return exportReportToBytes(jasperPrint, exporter);
		} catch (VulpeSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new VulpeSystemException(e, "vulpe.error.report");
		}
	}

	protected byte[] exportReportToBytes(final JasperPrint jasperPrint,
			final JRExporter exporter) throws JRException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.exportReport();
		return baos.toByteArray();
	}

	public DownloadInfo getDownloadInfo(
			final Collection<VulpeBaseEntity<?>> collection, final String fileName,
			final String[] subReports, final String format) {
		String contentType = null;
		if (format.equals(ReportUtil.FORMAT_CSV)) {
			contentType = "text/plain";
		} else if (format.equals(ReportUtil.FORMAT_HTML)) {
			contentType = "text/html";
		} else if (format.equals(ReportUtil.FORMAT_XLS)) {
			contentType = "application/vnd.ms-excel";
		} else if (format.equals(ReportUtil.FORMAT_XML)) {
			contentType = "text/xml";
		} else if (format.equals(ReportUtil.FORMAT_RTF)) {
			contentType = "application/rtf";
		} else {
			contentType = "application/pdf";
		}

		final byte data[] = getJasperReport(fileName, subReports, collection,
				format);

		return data == null ? null : new DownloadInfo(data, contentType);
	}

	public DownloadInfo getDownloadInfo(
			final Collection<VulpeBaseEntity<?>> collection, final String fileName,
			final String[] subReports, final String format,
			final String reportName, final boolean reportDownload) {
		DownloadInfo downloadInfo = getDownloadInfo(collection, fileName,
				subReports, format);
		downloadInfo.setName(reportName.concat(".")
				.concat(format.toLowerCase()));
		String contentDisposition = reportDownload ? "attachment; "
				: "inline; ";
		downloadInfo.setContentDisposition(contentDisposition.concat(
				"filename=\"").concat(downloadInfo.getName()).concat("\""));
		return downloadInfo;
	}
}