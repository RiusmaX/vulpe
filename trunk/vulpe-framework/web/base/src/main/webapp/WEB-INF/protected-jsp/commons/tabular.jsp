<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="targetConfig" value="${now['controllerConfig'].tabularConfig}" scope="request"/>
<c:set var="targetConfigPropertyName" value="${now['controllerConfig'].tabularConfig.propertyName}" scope="request"/>
<div id="vulpeTabular-${now['controllerConfig'].tabularConfig.baseName}">
	<div id="vulpeTabularActions-${now['controllerConfig'].tabularConfig.baseName}" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/tabularActions.jsp" %>
	</div>
	<c:if test="${targetConfig.showFilter}">
	<div id="vulpeTabularSelect-${targetConfig.baseName}">
		<jsp:include page="${now['controllerConfig'].viewSelectPath}" />
	</div>
	</c:if>
	<div id="vulpeTabularBody-${targetConfig.baseName}">
		<jsp:include page="${now['controllerConfig'].viewPath}" />
	</div>
</div>