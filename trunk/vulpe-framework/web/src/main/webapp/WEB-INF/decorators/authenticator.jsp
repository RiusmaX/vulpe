<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<fmt:setBundle basename="${global['i18nManager']}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate, post-check=0, pre-check=0"/>
		<c:if test="${global['showAsMobile']}">
		<meta name="viewport" content="width=${global['viewportWidth']}, height=${global['viewportHeight']}, user-scalable=${global['viewportUserScalable']}, initial-scale=${global['viewportInitialScale']}, maximum-scale=${global['viewportMaximumScale']}, minimum-scale=${global['viewportMinimumScale']}" />
		</c:if>
		<title><fmt:message key="vulpe.security.title.application"/></title>
		<link type="image/x-icon" href="${pageContext.request.contextPath}/themes/${global['theme']}/images/icon.png" rel="shortcut icon"/>
		<%@include file="/WEB-INF/protected-jsp/commons/javascript.jsp" %>
		<%@include file="/WEB-INF/protected-jsp/commons/css.jsp" %>
		<decorator:head/>
	</head>
	<body>
		<div id="authenticator" align="${global['backendCenteredLayout'] ? 'center' : ''}">
			<div id="loading" style="display: none;"></div>
			<div id="body">
				<decorator:body/>
			</div>
		</div>
	</body>
</html>