<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<div id="vulpeFrontend">
	<div id="vulpeFrontendActions" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/frontendActions.jsp"%>
	</div>
	<div id="vulpeFrontendBody">
		<jsp:include page="${now['controllerConfig'].viewPath}" />
	</div>
	<c:if test="${not empty frontendFooter}">
	<div id="vulpeFrontendFooter">
	</div>
	</c:if>
</div>