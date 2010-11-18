<c:set var="layout" value="${vulpeCurrentLayout == 'FRONTEND' ? 'frontend/' : ''}"/>
<style media="all" type="text/css">
	@import "${pageContext.request.contextPath}/css/vulpe.css";
	@import "${pageContext.request.contextPath}/themes/${global['theme']}/css/${layout}${global['theme']}.css";
	<c:if test="${(vulpeCurrentLayout == 'FRONTEND' && global['frontendMenuType'] == 'DROPPY') || (vulpeCurrentLayout == 'BACKEND' && global['backendMenuType'] == 'DROPPY')}">
	@import "${pageContext.request.contextPath}/themes/${global['theme']}/css/${layout}jquery.droppy.css";
	</c:if>
	<c:if test="${(vulpeCurrentLayout == 'FRONTEND' && global['frontendMenuType'] == 'SUPERFISH') || (vulpeCurrentLayout == 'BACKEND' && global['backendMenuType'] == 'SUPERFISH')}">
	@import "${pageContext.request.contextPath}/themes/${global['theme']}/css/${layout}jquery.superfish.css";
	</c:if>
	@import "${pageContext.request.contextPath}/themes/${global['theme']}/css/${layout}jquery.growl.css";
	@import "${pageContext.request.contextPath}/themes/${global['theme']}/css/${layout}jquery.lightbox.css";
	@import "${pageContext.request.contextPath}/themes/${global['theme']}/css/${layout}jquery.simplemodal.css";
	@import "${pageContext.request.contextPath}/themes/${global['theme']}/css/${layout}jquery.rte.css";
	@import "${pageContext.request.contextPath}/themes/${global['theme']}/css/${layout}jquery.ui.css";
	<!--[if lt IE 7]>
	@import "${pageContext.request.contextPath}/themes/${global['theme']}/css/${layout}jquery.simplemodal-ie.css?media=screen";
	<![endif]-->
</style>
<%@include file="/WEB-INF/protected-jsp/commons/cssExtended.jsp"%>