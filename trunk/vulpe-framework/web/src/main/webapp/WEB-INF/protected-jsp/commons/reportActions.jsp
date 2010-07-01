<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<p>
	<c:set var="style" value="display: none;"/>
	<c:if test="${prepareShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action validate="false" style="${style}" labelKey="vulpe.label.clear" elementId="vulpeButtonPrepare_${vulpeFormName}" action="${controllerConfig.controllerName}/prepare"/>

	<c:set var="style" value="display: none;"/>
	<c:if test="${clearShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.clear" elementId="vulpeButtonClear_${vulpeFormName}" javascript="document.forms['${vulpeFormName}'].reset();"/>

	<c:set var="style" value="display: none;"/>
	<c:if test="${readShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.view" elementId="vulpeButtonRead_${vulpeFormName}" action="${controllerConfig.controllerName}/read" layer="vulpeReportTable_${vulpeFormName}" />

</p>