<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<c:set var="index" value="" />
<c:if test="${not empty targetConfig.parentDetailConfig}">
	<c:set var="indexEL"
		value="${'${'}${targetConfig.parentDetailConfig.baseName}_status.index${'}'}" />
	<c:set var="currentDetailIndex" value="${util:eval(pageContext, indexEL)}" />
</c:if>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/detailActionsPrepend.jsp"%>
<c:set var="buttonDetailName" value="addDetail${targetConfig.baseName}" />
<v:action
	layerFields="body"
	validate="false" labelKey="addDetail"
	elementId="AddDetail-${targetConfig.baseName}"
	action="addDetail"
	queryString="detail=${targetConfigPropertyName}&detailLayer=vulpeDetailBody-${targetConfigLocal.baseName}${currentDetailIndex}" showButtonAsImage="false"
	layer="vulpeDetailBody-${targetConfigLocal.baseName}${currentDetailIndex}" config="${util:buttonConfig(pageContext, buttonDetailName, '')}" />
<%@include file="/WEB-INF/protected-jsp/commons/detailActionsAppend.jsp"%>
</p>