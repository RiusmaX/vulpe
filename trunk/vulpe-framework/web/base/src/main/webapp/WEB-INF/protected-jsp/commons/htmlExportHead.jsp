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
<%@include file="/WEB-INF/protected-jsp/commons/cssExport.jsp" %>
<decorator:head/>