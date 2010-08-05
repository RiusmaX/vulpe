<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/crudActionsExtended.jsp"%>
<c:if test="${prepareShow}">
<v:action validate="false" labelKey="clear" elementId="Prepare" action="prepare"/>
</c:if>
<c:if test="${clearShow}">
<v:action labelKey="clear" elementId="Clear" javascript="document.forms['${vulpeFormName}'].reset();"/>
</c:if>
<c:if test="${readShow}">
<v:action labelKey="view" elementId="Read" action="read" layer="vulpeReportTable_${vulpeFormName}" />
</c:if>
</p>