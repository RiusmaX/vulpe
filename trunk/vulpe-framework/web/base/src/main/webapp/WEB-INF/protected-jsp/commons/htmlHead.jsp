<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate, post-check=0, pre-check=0"/>
<c:if test="${global['project-mobile-enabled']}">
<meta name="viewport" content="width=${global['project-mobile-viewportWidth']}, height=${global['project-mobile-viewportHeight']}, user-scalable=${global['project-mobile-viewportUserScalable']}, initial-scale=${global['project-mobile-viewportInitialScale']}, maximum-scale=${global['project-mobile-viewportMaximumScale']}, minimum-scale=${global['project-mobile-viewportMinimumScale']}" />
</c:if>
<title>${htmlTitle}</title>
<link type="image/x-icon" href="${pageContext.request.contextPath}/themes/${global['project-theme']}/images/icon.png" rel="shortcut icon"/>
<c:if test="${empty vulpeCurrentLayout}">
	<c:set var="vulpeCurrentLayout" value="FRONTEND" scope="session"/>
</c:if>
<c:if test="${not empty SPRING_SECURITY_SAVED_REQUEST_KEY}">
	<c:choose>
		<c:when test="${fn:contains(SPRING_SECURITY_SAVED_REQUEST_KEY, '/frontend')}">
			<c:set var="vulpeCurrentLayout" value="FRONTEND" scope="session"/>
		</c:when>
		<c:otherwise>
			<c:set var="vulpeCurrentLayout" value="BACKEND" scope="session"/>
		</c:otherwise>
	</c:choose>
</c:if>
<%@include file="/WEB-INF/protected-jsp/commons/javascript.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/css.jsp" %>
<decorator:head/>