<c:set var="layout" value="${ever['vulpeCurrentLayout'] == 'FRONTEND' ? 'frontend/' : 'backend/'}"/>
<style media="all" type="text/css">
	@import "${pageContext.request.contextPath}/css/vulpe.css";
	@import "${pageContext.request.contextPath}/themes/${global['application-theme']}/css/${layout}${global['application-theme']}.css";
	<c:if test="${(ever['vulpeCurrentLayout'] == 'FRONTEND' && global['application-view-frontendMenuType'] == 'VULPE') || (ever['vulpeCurrentLayout'] == 'BACKEND' && global['application-view-backendMenuType'] == 'VULPE')}">
	@import "${pageContext.request.contextPath}/themes/${global['application-theme']}/css/${layout}vulpe.menu.css";
	</c:if>
	<c:if test="${(ever['vulpeCurrentLayout'] == 'FRONTEND' && global['application-view-frontendMenuType'] == 'DROPPY') || (ever['vulpeCurrentLayout'] == 'BACKEND' && global['application-view-backendMenuType'] == 'DROPPY')}">
	@import "${pageContext.request.contextPath}/themes/${global['application-theme']}/css/${layout}jquery.droppy.css";
	</c:if>
	<c:if test="${(ever['vulpeCurrentLayout'] == 'FRONTEND' && global['application-view-frontendMenuType'] == 'SUPERFISH') || (ever['vulpeCurrentLayout'] == 'BACKEND' && global['application-view-backendMenuType'] == 'SUPERFISH')}">
	@import "${pageContext.request.contextPath}/themes/${global['application-theme']}/css/${layout}jquery.superfish.css";
	</c:if>
	@import "${pageContext.request.contextPath}/themes/${global['application-theme']}/css/${layout}jquery.growl.css";
	@import "${pageContext.request.contextPath}/themes/${global['application-theme']}/css/${layout}jquery.lightbox.css";
	@import "${pageContext.request.contextPath}/themes/${global['application-theme']}/css/${layout}jquery.simplemodal.css";
	@import "${pageContext.request.contextPath}/themes/${global['application-theme']}/css/${layout}jquery.rte.css";
	@import "${pageContext.request.contextPath}/themes/${global['application-theme']}/css/${layout}jquery.ui.${global['application-view-jQueryUI']}.css";
	<!--[if lt IE 7]>
	@import "${pageContext.request.contextPath}/themes/${global['application-theme']}/css/${layout}jquery.simplemodal_ie.css?media=screen";
	<![endif]-->
</style>
<%@include file="/WEB-INF/protected-jsp/commons/cssExtended.jsp"%>