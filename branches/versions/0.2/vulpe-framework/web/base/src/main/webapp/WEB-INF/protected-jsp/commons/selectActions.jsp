<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/selectActionsPrepend.jsp"%>
<c:set var="layer" value="body" />
<c:if test="${vulpeBodyTwice}"><c:set var="layer" value="select" /></c:if>
<v:action validate="false" layer="${popup ? popupKey : layer}" labelKey="clear" elementId="Clear" action="clear" icon="clear" iconClass="Clear" render="${now['buttons']['clear'] || now['buttons']['SELECT_clear']}" />
<v:action validate="false" layer="${popup ? popupKey : ''}" labelKey="create" elementId="Create" action="create" beforeJs="vulpe.view.resetFields(%27${vulpeFormName}%27)" helpKey="create" icon="add" iconClass="Create" render="${!onlyToSee && (now['buttons']['create'] || now['buttons']['SELECT_create'])}" />
<v:action labelKey="read" elementId="Read" layer="vulpeSelectTable-${vulpeFormName}" action="read" beforeJs="vulpe.view.prepareRead(%27${vulpeFormName}%27)" helpKey="read" icon="search" iconClass="Read" render="${now['buttons']['read'] || now['buttons']['SELECT_read']}" />
<v:action labelKey="report" elementId="Report" layer="vulpeSelectTable-${vulpeFormName}" action="${controllerConfig.reportControllerName}/report/ajax" helpKey="report" icon="report" iconClass="Report" render="${now['buttons']['report'] || now['buttons']['SELECT_report']}" />
<c:if test="${not empty urlBack}">
	<v:action validate="false" labelKey="back" elementId="Back" action="${urlBack}"	helpKey="back" icon="back" iconClass="Back" />
	<c:remove var="urlBack" scope="session" />
	<c:remove var="layerUrlBack" scope="session" />
</c:if>
<%@include file="/WEB-INF/protected-jsp/commons/selectActionsAppend.jsp"%>
</p>