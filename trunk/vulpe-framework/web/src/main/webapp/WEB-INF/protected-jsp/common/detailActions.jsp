<%@include file="/WEB-INF/protected-jsp/common/common.jsp"%>

<c:set var="index" value="" />
<c:if test="${not empty targetConfig.parentDetailConfig}">
	<c:set var="indexEL"
		value="${'${'}${targetConfig.parentDetailConfig.baseName}_status.index${'}'}" />
	<c:set var="index" value="_${util:eval(pageContext, indexEL)}_" />
</c:if>

<p><c:set var="btEL"
	value="${'${'}addDetailShow${targetConfig.baseName}${'}'}" /> <c:set
	var="bt" value="${util:eval(pageContext, btEL)}" /> <c:set var="style"
	value="display: none;" /> <c:if test="${bt}">
	<c:set var="style" value="display: inline;" />
</c:if> <v:action
	layerFields="${actionConfig.formName}_${targetConfig.baseName}${index}"
	validate="false" style="${style}" labelKey="vulpe.label.addDetail"
	elementId="bt_addDetail_${actionConfig.formName}_${targetConfig.baseName}"
	action="${actionConfig.primitiveActionName}/addDetail/ajax"
	queryString="detail=${targetConfigPropertyName}"
	layer="${actionConfig.formName}_${targetConfig.baseName}${index}_detail_body" />

</p>