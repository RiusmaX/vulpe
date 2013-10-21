<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<fmt:setBundle basename="${global['application-i18nManager']}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<fmt:message key="vulpe.export.title.application" scope="request" var="htmlTitle"/>
		<%@include file="/WEB-INF/protected-jsp/commons/htmlExportHead.jsp" %>
	</head>
	<body>
		<%@include file="/WEB-INF/protected-jsp/commons/htmlExportBody.jsp" %>
	</body>
</html>