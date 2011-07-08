<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<c:set var="index" value="" />
<c:if test="${not empty now['targetConfig'].parentDetailConfig}">
	<c:set var="indexEL"
		value="${'${'}${now['targetConfig'].parentDetailConfig.baseName}_status.index${'}'}" />
	<c:set var="currentDetailIndex" value="${util:eval(pageContext, indexEL)}" />
</c:if>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/detailActionsPrepend.jsp"%>
<c:set var="buttonDetailName" value="addDetail${now['targetConfig'].baseName}" />
<v:action
	layerFields="body"
	validate="false" labelKey="addDetail"
	elementId="AddDetail-${now['targetConfig'].baseName}"
	action="addDetail"
	queryString="detail=${now['now['targetConfig']PropertyName']}&detailLayer=vulpeDetailBody-${now['targetConfig']Local.baseName}${currentDetailIndex}" showButtonAsImage="false"
	layer="vulpeDetailBody-${now['targetConfig']Local.baseName}${currentDetailIndex}" config="${util:buttonConfig(pageContext, buttonDetailName, '')}" />
<%@include file="/WEB-INF/protected-jsp/commons/detailActionsAppend.jsp"%>
</p>