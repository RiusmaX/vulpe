<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="targetConfig" value="${actionConfig.tabularConfig}" scope="request"/>
<c:set var="targetConfigPropertyName" value="${actionConfig.tabularConfig.propertyName}" scope="request"/>
<div id="vulpeTabular_${actionConfig.tabularConfig.baseName}">
	<div id="vulpeTabularActions_${actionConfig.tabularConfig.baseName}" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/tabularActions.jsp" %>
	</div>
	<div id="vulpeTabularBody_${actionConfig.tabularConfig.baseName}">
		<jsp:include page="${actionConfig.viewPath}" />
	</div>
</div>