<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>
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
		<c:set var="vulpeCurrentLayout" value="AUTHENTICATOR" scope="session"/>
		<%@include file="/WEB-INF/protected-jsp/common/javascript.jsp" %>
		<%@include file="/WEB-INF/protected-jsp/common/css.jsp" %>
		<decorator:head/>
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