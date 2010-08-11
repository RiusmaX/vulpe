<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="layout" value="${vulpeCurrentLayout == 'FRONTEND' ? 'frontend/' : ''}"/>
<fmt:setBundle basename="${global['i18nManager']}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message
	key="vulpe.error.internal.title" /></title>
<style media="all" type="text/css">
@import "${pageContext.request.contextPath}/css/frontend/vulpe.css";
@import	"${pageContext.request.contextPath}/themes/${global['theme']}/css/${layout}${global['theme']}.css";
</style>
</head>
<body>
<!--IS_EXCEPTION-->
<div id="messageTitle" style="display: none"><fmt:message key="vulpe.error.internal.title" /></div>
<div id="error">
	<h1><fmt:message key="vulpe.error.internal" /></h1>
	<div id="message">
		<strong><fmt:message key="label.vulpe.message" />:</strong>
		${pageContext.errorData.throwable}
	</div>
</div>
</body>
</html>