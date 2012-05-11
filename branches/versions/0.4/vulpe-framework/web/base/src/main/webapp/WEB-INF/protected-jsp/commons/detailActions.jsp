<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<c:set var="index" value="" />
<c:if test="${not empty targetConfig.parentDetailConfig}">
	<c:set var="indexEL"
		value="${'${'}${targetConfig.parentDetailConfig.baseName}_status.index${'}'}" />
</c:if>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/detailActionsPrepend.jsp"%>
<c:set var="buttonDetailName" value="addDetail${targetConfig.baseName}" />
<v:action
	layerFields="body"
	validate="false" labelKey="addDetail"
	elementId="AddDetail-${targetConfig.baseName}"
	action="addDetail"
	queryString="now.detail=${targetConfigPropertyName}&now.detailLayer=vulpeDetailBody-${targetConfigLocal.baseName}" showButtonAsImage="false" showIconOfButton="false"
	layer="vulpeDetailBody-${targetConfigLocal.baseName}" config="${util:buttonConfig(pageContext, buttonDetailName, '')}" />
<%@include file="/WEB-INF/protected-jsp/commons/detailActionsAppend.jsp"%>
</p>