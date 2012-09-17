<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<div id="vulpeBackend">
	<div id="vulpeBackendActions" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/backendActions.jsp"%>
	</div>

	<div id="vulpeBackendBody">
		<jsp:include page="${now['controllerConfig'].viewPath}" />
	</div>

	<c:if test="${not empty backendFooter}">
	<div id="vulpeBackendFooter">
	</div>
	</c:if>
</div>