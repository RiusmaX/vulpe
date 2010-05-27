<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<p>
	<c:set var="style" value="display: none;"/>
	<c:if test="${prepareShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action validate="false" style="${style}" labelKey="vulpe.label.clear" elementId="bt_prepare_${actionConfig.formName}" action="${actionConfig.primitiveActionName}/prepare"/>

	<c:set var="style" value="display: inline;"/>
	<c:if test="${clearShow == false}">
		<c:set var="style" value="display: none;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.clear" elementId="bt_clear_${actionConfig.formName}" javascript="document.forms['${actionConfig.formName}'].reset();"/>

	<c:set var="style" value="display: none;"/>
	<c:if test="${readShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.view" elementId="bt_read_${actionConfig.formName}" action="${actionConfig.primitiveActionName}/read" layer="${actionConfig.formName}_report_table" />

</p>