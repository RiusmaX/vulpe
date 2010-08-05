<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/selectActionsExtended.jsp"%>
<c:set var="layer" value="body" />
<c:if test="${vulpeBodyTwice}">
	<c:set var="layer" value="select" />
</c:if>
<c:if test="${clearShow || SELECT_clearShow}">
<v:action labelKey="clear" elementId="Clear" javascript="document.forms['${vulpeFormName}'].reset();" icon="clear" iconClass="Clear" />
</c:if>
<c:if test="${prepareShow || SELECT_prepareShow}">
<v:action validate="false" layer="${popup ? popupKey : layer}" labelKey="clear" elementId="Prepare" action="prepare" helpKey="clear" icon="clear" iconClass="Prepare" />
</c:if>
<c:if test="${createShow || SELECT_createShow}">
<v:action validate="false" layer="${popup ? popupKey : ''}" labelKey="create" elementId="Create" action="create" beforeJs="vulpe.view.resetFields(%27${vulpeFormName}%27)" helpKey="create" icon="add" iconClass="Create" />
</c:if>
<c:if test="${readShow || SELECT_readShow}">
<v:action labelKey="read" elementId="Read" layer="vulpeSelectTable_${vulpeFormName}" action="read" beforeJs="vulpe.view.prepareRead(%27${vulpeFormName}%27)" helpKey="read" icon="search" iconClass="Read" />
</c:if>
<c:if test="${reportShow || SELECT_reportShow}">
<v:action labelKey="report" elementId="Report" layer="vulpeSelectTable_${vulpeFormName}" action="${controllerConfig.reportControllerName}/read/ajax" helpKey="report" icon="report" iconClass="Report" />
</c:if>
</p>