<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<p>
	<c:set var="style" value="display: none;"/>
	<c:if test="${prepareShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action validate="false" style="${style}" labelKey="vulpe.label.clear" elementId="vulpeButtonPrepare_${controllerConfig.formName}" action="${controllerConfig.controllerName}/prepare"/>

	<c:set var="style" value="display: inline;"/>
	<c:if test="${clearShow == false}">
		<c:set var="style" value="display: none;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.clear" elementId="vulpeButtonClear_${controllerConfig.formName}" javascript="document.forms['${controllerConfig.formName}'].reset();"/>

	<c:set var="style" value="display: none;"/>
	<c:if test="${readShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.view" elementId="vulpeButtonRead_${controllerConfig.formName}" action="${controllerConfig.controllerName}/read" layer="vulpeReportTable_${controllerConfig.formName}" />

</p>