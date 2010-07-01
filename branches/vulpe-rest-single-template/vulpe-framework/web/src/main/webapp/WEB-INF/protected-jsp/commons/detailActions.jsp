<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>

<c:set var="index" value="" />
<c:if test="${not empty targetConfig.parentDetailConfig}">
	<c:set var="indexEL"
		value="${'${'}${targetConfig.parentDetailConfig.baseName}_status.index${'}'}" />
	<c:set var="currentDetailIndex" value="${util:eval(pageContext, indexEL)}" />
</c:if>

<p><c:set var="buttonDetailEL"
	value="${'${'}addDetailShow${targetConfig.baseName}${'}'}" /> <c:set
	var="buttonDetail" value="${util:eval(pageContext, buttonDetailEL)}" /> <c:set var="style"
	value="display: none;" /> <c:if test="${buttonDetail}">
	<c:set var="style" value="display: inline;" />
</c:if> <v:action
	layerFields="vulpeDetailBody_${targetConfigLocal.baseName}${currentDetailIndex}"
	validate="false" style="${style}" labelKey="vulpe.label.addDetail"
	elementId="vulpeButtonAddDetail_${controllerConfig.formName}_${targetConfig.baseName}"
	action="${controllerConfig.controllerName}/addDetail/ajax"
	queryString="detail=${targetConfigPropertyName}" showButtonAsImage="false"
	layer="vulpeDetailBody_${targetConfigLocal.baseName}${currentDetailIndex}" />

</p>