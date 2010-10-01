<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/selectActionsExtended.jsp"%>
<c:set var="layer" value="body" />
<c:if test="${vulpeBodyTwice}">
	<c:set var="layer" value="select" />
</c:if>
<c:if test="${now['buttons']['clear'] || now['buttons']['SELECT_clear']}">
<v:action labelKey="clear" elementId="Clear" javascript="document.forms['${vulpeFormName}'].reset();" icon="clear" iconClass="Clear" />
</c:if>
<c:if test="${now['buttons']['prepare'] || now['buttons']['SELECT_prepare']}">
<v:action validate="false" layer="${popup ? popupKey : layer}" labelKey="clear" elementId="Prepare" action="prepare" helpKey="clear" icon="clear" iconClass="Prepare" />
</c:if>
<c:if test="${!onlyToSee && (now['buttons']['create'] || now['buttons']['SELECT_create'])}">
<v:action validate="false" layer="${popup ? popupKey : ''}" labelKey="create" elementId="Create" action="create" beforeJs="vulpe.view.resetFields(%27${vulpeFormName}%27)" helpKey="create" icon="add" iconClass="Create" />
</c:if>
<c:if test="${now['buttons']['read'] || now['buttons']['SELECT_read']}">
<v:action labelKey="read" elementId="Read" layer="vulpeSelectTable-${vulpeFormName}" action="read" beforeJs="vulpe.view.prepareRead(%27${vulpeFormName}%27)" helpKey="read" icon="search" iconClass="Read" />
</c:if>
<c:if test="${now['buttons']['report'] || now['buttons']['SELECT_report']}">
<v:action labelKey="report" elementId="Report" layer="vulpeSelectTable-${vulpeFormName}" action="${controllerConfig.reportControllerName}/report/ajax" helpKey="report" icon="report" iconClass="Report" />
</c:if>
</p>