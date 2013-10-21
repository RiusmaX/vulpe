<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="layout" value="${ever['vulpeCurrentLayout'] == 'FRONTEND' ? 'frontend/' : 'backend/'}"/>
<fmt:setBundle basename="${global['application-i18nManager']}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="vulpe.error.408.title" /></title>
<style media="all" type="text/css">
@import "${pageContext.request.contextPath}/css/frontend/vulpe.css";
@import	"${pageContext.request.contextPath}/themes/${global['application-theme']}/css/${layout}${global['application-theme']}.css";
</style>
</head>
<body>
<!--IS_EXCEPTION-->
<div id="messageTitle" style="display: none"><fmt:message key="vulpe.error.408.title" /></div>
<div id="error">
	<h1><fmt:message key="vulpe.error.408.subtitle" /></h1>
	<h2><fmt:message key="vulpe.error.408" /></h2>
	<br/>
	<div id="home">
		<a href="${pageContext.request.contextPath}"><fmt:message key="label.vulpe.home" /></a>
	</div>
	<br/>
</div>
</body>
</html>