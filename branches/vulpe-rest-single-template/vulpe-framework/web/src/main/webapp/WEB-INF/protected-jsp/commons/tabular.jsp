<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="targetConfig" value="${controllerConfig.tabularConfig}" scope="request"/>
<c:set var="targetConfigPropertyName" value="${controllerConfig.tabularConfig.propertyName}" scope="request"/>
<div id="vulpeTabular_${controllerConfig.tabularConfig.baseName}">
	<div id="vulpeTabularActions_${controllerConfig.tabularConfig.baseName}" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/tabularActions.jsp" %>
	</div>
	<div id="vulpeTabularBody_${controllerConfig.tabularConfig.baseName}">
		<jsp:include page="${controllerConfig.viewPath}" />
	</div>
</div>