<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="${vulpeI18nManager}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="vulpe.error.404.title" /></title>
<style media="all" type="text/css">
@import "${pageContext.request.contextPath}/css/frontend/vulpe.css";
@import	"${pageContext.request.contextPath}/themes/${vulpeTheme}/css/frontend/${vulpeTheme}.css";
</style>
</head>
<body>
<!--IS_EXCEPTION-->
<div id="error">
<h1><fmt:message key="vulpe.error.404.subtitle" /></h1>
<h2><fmt:message key="vulpe.error.404" /></h2>
<div id="home"><a href="${pageContext.request.contextPath}"><fmt:message
	key="vulpe.label.home" /></a></div>
</div>
</body>
</html>