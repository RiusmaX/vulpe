<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>

<c:set var="targetConfig" value="${actionConfig.tabularConfig}" scope="request"/>
<c:set var="targetConfigPropertyName" value="${actionConfig.tabularConfig.propertyName}" scope="request"/>
<div id="${actionConfig.formName}_${actionConfig.tabularConfig.baseName}_tabular">
	<div id="${actionConfig.formName}_${actionConfig.tabularConfig.baseName}_tabular_actions" class="actions">
		<%@include file="/WEB-INF/protected-jsp/common/tabularActions.jsp" %>
	</div>
	<div id="${actionConfig.formName}_${actionConfig.tabularConfig.baseName}_tabular_body">
		<jsp:include page="${actionConfig.viewPath}" />
	</div>
</div>