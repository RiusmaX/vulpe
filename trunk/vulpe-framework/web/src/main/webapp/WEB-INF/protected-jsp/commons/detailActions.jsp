<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<c:set var="index" value="" />
<c:if test="${not empty targetConfig.parentDetailConfig}">
	<c:set var="indexEL"
		value="${'${'}${targetConfig.parentDetailConfig.baseName}_status.index${'}'}" />
	<c:set var="currentDetailIndex" value="${util:eval(pageContext, indexEL)}" />
</c:if>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/detailActionsExtended.jsp"%>
<c:set var="buttonDetailEL" value="${'${'}now['buttons']['addDetail${targetConfig.baseName}']${'}'}" />
<c:set var="buttonDetail" value="${util:eval(pageContext, buttonDetailEL)}" />
<c:set var="style" value="display: none;" />
<c:if test="${buttonDetail}">
<v:action
	layerFields="vulpeDetailBody_${targetConfigLocal.baseName}${currentDetailIndex}"
	validate="false" labelKey="addDetail"
	elementId="AddDetail_${targetConfig.baseName}"
	action="addDetail"
	queryString="detail=${targetConfigPropertyName}" showButtonAsImage="false"
	layer="vulpeDetailBody_${targetConfigLocal.baseName}${currentDetailIndex}" />
</c:if>
</p>