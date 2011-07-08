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
package org.vulpe.controller.struts.util;

import java.io.File;
import java.net.URL;
import java.util.Collection;

import net.sf.jasperreports.engine.JRExporter;
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
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.controller.util.ReportUtil;
import org.vulpe.exception.VulpeSystemException;

/**
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * 
 */
public class StrutsReportUtil extends ReportUtil implements JasperReportConstants {
	/**
	 * Returns StrutsReportUtil instance
	 */
	public static StrutsReportUtil getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(StrutsReportUtil.class)) {
			cache.put(StrutsReportUtil.class, new StrutsReportUtil());
		}
		return cache.get(StrutsReportUtil.class);
	}

	protected StrutsReportUtil() {
		// default constructor
	}

	public byte[] getJasperReport(final String fileName, final String[] subReports,
			final Collection<?> collection, final VulpeHashMap<String, Object> parameters,
			final String format) {
		try {
			String fullFileName = getRealPath(fileName);
			if (StringUtils.isBlank(fullFileName)) {
				throw new VulpeSystemException("vulpe.error.report");
			}
			final JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fullFileName);
			final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(collection);

			parameters.put("BASEDIR", StringUtils.replace(fullFileName, StringUtils.replace(
					fileName, "/", File.separator), ""));
			if (subReports != null && subReports.length > 0) {
				int count = 0;
				for (String subReport : subReports) {
					parameters.put("SUBREPORT_".concat(String.valueOf(count)),
							getRealPath(subReport));
					++count;
				}
			}
			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					dataSource);
			if (jasperPrint == null || jasperPrint.getPages().isEmpty()) {
				return null;
			}
			if (format.equals(StrutsReportUtil.FORMAT_PDF)) {
				return JasperExportManager.exportReportToPdf(jasperPrint);
			}
			JRExporter exporter;
			if (format.equals(StrutsReportUtil.FORMAT_CSV)) {
				exporter = new JRCsvExporter();
			} else if (format.equals(StrutsReportUtil.FORMAT_HTML)) {
				exporter = new JRHtmlExporter();
			} else if (format.equals(StrutsReportUtil.FORMAT_XLS)) {
				exporter = new JRXlsExporter();
			} else if (format.equals(StrutsReportUtil.FORMAT_XML)) {
				exporter = new JRXmlExporter();
			} else if (format.equals(StrutsReportUtil.FORMAT_RTF)) {
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

	public DownloadInfo getDownloadInfo(final Collection<?> collection,
			final VulpeHashMap<String, Object> parameters, final String fileName,
			final String[] subReports, final String format, final String reportName,
			boolean reportDownload) {
		String contentType = null;
		if (format.equals(StrutsReportUtil.FORMAT_CSV)) {
			contentType = "text/plain";
			reportDownload = true;
		} else if (format.equals(StrutsReportUtil.FORMAT_HTML)) {
			contentType = "text/html";
		} else if (format.equals(StrutsReportUtil.FORMAT_XLS)) {
			contentType = "application/vnd.ms-excel";
			reportDownload = true;
		} else if (format.equals(StrutsReportUtil.FORMAT_XML)) {
			contentType = "text/xml";
		} else if (format.equals(StrutsReportUtil.FORMAT_RTF)) {
			contentType = "application/rtf";
			reportDownload = true;
		} else {
			contentType = "application/pdf";
		}
		final byte data[] = getJasperReport(fileName, subReports, collection, parameters, format);
		final DownloadInfo downloadInfo = data == null ? null : new DownloadInfo(data, contentType);
		if (downloadInfo != null) {
			downloadInfo.setName(reportName.concat(".").concat(format.toLowerCase()));
			final String contentDisposition = reportDownload ? "attachment; " : "inline; ";
			downloadInfo.setContentDisposition(contentDisposition.concat("filename=\"").concat(
					downloadInfo.getName()).concat("\""));
		}
		return downloadInfo;
	}

	private String getRealPath(final String fileName) {
		String realPath = "";
		final URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
		if (url != null) {
			realPath = url.getPath();
		}
		return realPath.replaceAll("%20", " ");
	}
}