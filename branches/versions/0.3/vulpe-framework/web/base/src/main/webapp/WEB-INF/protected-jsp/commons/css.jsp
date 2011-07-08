<c:set var="layout" value="${vulpeCurrentLayout == 'FRONTEND' ? 'frontend/' : ''}"/>
<style media="all" type="text/css">
	@import "${pageContext.request.contextPath}/css/vulpe.css";
	@import "${pageContext.request.contextPath}/themes/${global['project-theme']}/css/${layout}${global['project-theme']}.css";
	<c:if test="${(vulpeCurrentLayout == 'FRONTEND' && global['project-view-frontendMenuType'] == 'DROPPY') || (vulpeCurrentLayout == 'BACKEND' && global['project-view-backendMenuType'] == 'DROPPY')}">
	@import "${pageContext.request.contextPath}/themes/${global['project-theme']}/css/${layout}jquery.droppy.css";
	</c:if>
	<c:if test="${(vulpeCurrentLayout == 'FRONTEND' && global['project-view-frontendMenuType'] == 'SUPERFISH') || (vulpeCurrentLayout == 'BACKEND' && global['project-view-backendMenuType'] == 'SUPERFISH')}">
	@import "${pageContext.request.contextPath}/themes/${global['project-theme']}/css/${layout}jquery.superfish.css";
	</c:if>
	@import "${pageContext.request.contextPath}/themes/${global['project-theme']}/css/${layout}jquery.growl.css";
	@import "${pageContext.request.contextPath}/themes/${global['project-theme']}/css/${layout}jquery.lightbox.css";
	@import "${pageContext.request.contextPath}/themes/${global['project-theme']}/css/${layout}jquery.simplemodal.css";
	@import "${pageContext.request.contextPath}/themes/${global['project-theme']}/css/${layout}jquery.rte.css";
	@import "${pageContext.request.contextPath}/themes/${global['project-theme']}/css/${layout}jquery.ui.${global['project-view-jQueryUI']}.css";
	<!--[if lt IE 7]>
	@import "${pageContext.request.contextPath}/themes/${global['project-theme']}/css/${layout}jquery.simplemodal_ie.css?media=screen";
	<![endif]-->
</style>
<%@include file="/WEB-INF/protected-jsp/commons/cssExtended.jsp"%>