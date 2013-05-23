<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate, post-check=0, pre-check=0"/>
<c:if test="${global['application-mobile-enabled']}">
<meta name="viewport" content="width=${global['application-mobile-viewportWidth']}, height=${global['application-mobile-viewportHeight']}, user-scalable=${global['application-mobile-viewportUserScalable']}, initial-scale=${global['application-mobile-viewportInitialScale']}, maximum-scale=${global['application-mobile-viewportMaximumScale']}, minimum-scale=${global['application-mobile-viewportMinimumScale']}" />
</c:if>
<fmt:message key="vulpe.title.application" var="htmlTitle" />
<title>${not empty ever['vulpePortal'] ? ever['vulpePortal'].title : htmlTitle}</title>
<link type="image/x-icon" href="${pageContext.request.contextPath}/themes/${global['application-theme']}/images/icon.png" rel="shortcut icon"/>
<%@include file="/WEB-INF/protected-jsp/commons/javascript.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/css.jsp" %>
<decorator:head/>