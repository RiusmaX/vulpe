<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="targetConfig" value="${controllerConfig.tabularConfig}" scope="request"/>
<c:set var="targetConfigPropertyName" value="${controllerConfig.tabularConfig.propertyName}" scope="request"/>
<div id="vulpeTabular-${controllerConfig.tabularConfig.baseName}">
	<div id="vulpeTabularActions-${controllerConfig.tabularConfig.baseName}" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/tabularActions.jsp" %>
	</div>
	<c:if test="${targetConfig.showFilter}">
	<div id="vulpeTabularSelect-${targetConfig.baseName}">
		<jsp:include page="${controllerConfig.viewSelectPath}" />
	</div>
	</c:if>
	<div id="vulpeTabularBody-${targetConfig.baseName}">
		<jsp:include page="${controllerConfig.viewPath}" />
	</div>
</div>