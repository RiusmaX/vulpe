<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>

<c:set var="index" value="" />
<c:if test="${not empty targetConfig.parentDetailConfig}">
	<c:set var="indexEL"
		value="${'${'}${targetConfig.parentDetailConfig.baseName}_status.index${'}'}" />
	<c:set var="index" value="_${util:eval(pageContext, indexEL)}_" />
</c:if>

<p><c:set var="buttonDetailEL"
	value="${'${'}addDetailShow${targetConfig.baseName}${'}'}" /> <c:set
	var="buttonDetail" value="${util:eval(pageContext, buttonDetailEL)}" /> <c:set var="style"
	value="display: none;" /> <c:if test="${buttonDetail}">
	<c:set var="style" value="display: inline;" />
</c:if> <v:action
	layerFields="${controllerConfig.formName}_${targetConfig.baseName}${index}"
	validate="false" style="${style}" labelKey="vulpe.label.addDetail"
	elementId="vulpeButtonAddDetail_${controllerConfig.formName}_${targetConfig.baseName}"
	action="${controllerConfig.primitiveActionName}/addDetail/ajax"
	queryString="detail=${targetConfigPropertyName}" showButtonAsImage="false"
	layer="vulpeDetailBody_${targetConfig.baseName}${index}" />

</p>