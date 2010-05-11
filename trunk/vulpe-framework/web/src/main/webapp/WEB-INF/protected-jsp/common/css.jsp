<c:set var="layout" value="${vulpeCurrentLayout == 'FRONTEND' ? 'frontend/' : ''}"/>
<style media="all" type="text/css">
	@import "${pageContext.request.contextPath}/css/${layout}vulpe.css";
	<c:if test="${vulpeCurrentLayout == 'AUTHENTICATOR'}">
	@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/authenticator.css";
	</c:if>
	@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/${layout}${vulpeTheme}.css";
	@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/${layout}jquery.accordion.css";
	<c:if test="${(vulpeCurrentLayout == 'FRONTEND' && vulpeFrontendMenuType == 'DROPPY') || (vulpeCurrentLayout == 'BACKEND' && vulpeBackendMenuType == 'DROPPY')}">
	@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/${layout}jquery.droppy.css";
	</c:if>
	@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/${layout}jquery.lightbox.css";
	@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/${layout}jquery.simplemodal.css";
	@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/${layout}jquery.rte.css";
	@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/${layout}jquery.ui.css";
	<!--[if lt IE 7]>
	@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/${layout}jquery.simplemodal_ie.css?media=screen";
	<![endif]-->
</style>