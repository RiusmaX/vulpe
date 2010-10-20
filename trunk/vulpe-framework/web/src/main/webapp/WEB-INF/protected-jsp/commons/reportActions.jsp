<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/crudActionsPrepend.jsp"%>
<c:if test="${now['buttons']['prepare']}"><v:action validate="false" labelKey="clear" elementId="Prepare" action="prepare"/></c:if>
<c:if test="${now['buttons']['clear']}"><v:action labelKey="clear" elementId="Clear" javascript="document.forms['${vulpeFormName}'].reset();"/></c:if>
<c:if test="${now['buttons']['read']}"><v:action labelKey="view" elementId="Read" action="read" layer="vulpeReportTable-${vulpeFormName}" /></c:if>
<%@include file="/WEB-INF/protected-jsp/commons/crudActionsAppend.jsp"%>
</p>