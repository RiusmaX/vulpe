<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/detailActionsPrepend.jsp"%>
<c:set var="buttonDetailName" value="addDetail${targetConfig.baseName}" />
<c:set var="layerName" value="vulpeDetailBody-${targetConfigLocal.baseName}${index}" />
<v:action
	layerFields="body"
	validate="false" labelKey="addDetail"
	elementId="AddDetail-${targetConfig.baseName}"
	action="addDetail"
	queryString="now.detail=${targetConfigPropertyName}&now.detailLayer=${layerName}&now.detailIndex=${index}" showButtonAsImage="false" showIconOfButton="false"
	layer="${layerName}" config="${util:buttonConfig(pageContext, buttonDetailName, '')}" />
<%@include file="/WEB-INF/protected-jsp/commons/detailActionsAppend.jsp"%>
</p>