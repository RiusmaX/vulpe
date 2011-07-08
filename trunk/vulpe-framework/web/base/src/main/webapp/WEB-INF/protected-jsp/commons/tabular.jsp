<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="now['targetConfig']" value="${now['controllerConfig'].tabularConfig}" scope="request"/>
<c:set var="now['now['targetConfig']PropertyName']" value="${now['controllerConfig'].tabularConfig.propertyName}" scope="request"/>
<div id="vulpeTabular-${now['controllerConfig'].tabularConfig.baseName}">
	<div id="vulpeTabularActions-${now['controllerConfig'].tabularConfig.baseName}" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/tabularActions.jsp" %>
	</div>
	<c:if test="${now['targetConfig'].showFilter}">
	<div id="vulpeTabularSelect-${now['targetConfig'].baseName}">
		<jsp:include page="${now['controllerConfig'].viewSelectPath}" />
	</div>
	</c:if>
	<div id="vulpeTabularBody-${now['targetConfig'].baseName}">
		<jsp:include page="${now['controllerConfig'].viewPath}" />
	</div>
</div>