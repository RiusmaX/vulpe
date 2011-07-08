<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<fmt:setBundle basename="${global['project-i18nManager']}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<c:set var="vulpeCurrentLayout" value="BACKEND" scope="session"/>
		<fmt:message key="vulpe.title.application" scope="request" var="htmlTitle"/>
		<%@include file="/WEB-INF/protected-jsp/commons/htmlHead.jsp" %>
	</head>
	<body>
		<%@include file="/WEB-INF/protected-jsp/commons/htmlBody.jsp" %>
	</body>
</html>