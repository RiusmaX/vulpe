<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="${vulpeI18nManager}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate, post-check=0, pre-check=0"/>
		<c:if test="${vulpeShowAsMobile}">
		<meta name="viewport" content="width=${viewportWidth}, height=${viewportHeight}, user-scalable=${viewportUserScalable}, initial-scale=${viewportInitialScale}, maximum-scale=${viewportMaximumScale}, minimum-scale=${viewportMinimumScale}" />
		</c:if>
		<title><fmt:message key="vulpe.security.title.application"/></title>
		<link type="image/x-icon" href="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icon.png" rel="shortcut icon"/>
		<script src="${pageContext.request.contextPath}/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.form.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.maskedinput.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.simplemodal.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.ui.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.validation.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/bodyoverlay.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/vulpe.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/application.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/themes/${vulpeTheme}/js/${vulpeTheme}.js" type="text/javascript" charset="utf-8"></script>
		<%@include file="/WEB-INF/protected-jsp/common/javascript.jsp" %>
		<style media="all" type="text/css">
			@import "${pageContext.request.contextPath}/css/vulpe.css";
			@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/authenticator.css";
			@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/jquery.simplemodal.css";
			@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/jquery.ui.css";
		</style>
		<decorator:head/>
		<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>
	</head>
	<body>
		<div id="authenticator" align="${vulpeBackendCenteredLayout ? 'center' : ''}">
			<div id="loading" style="display: none;"></div>
			<div id="body">
				<decorator:body/>
			</div>
		</div>
	</body>
</html>