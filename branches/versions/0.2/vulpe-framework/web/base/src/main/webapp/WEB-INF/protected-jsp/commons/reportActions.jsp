<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsPrepend.jsp"%>
<v:action validate="false" labelKey="clear" elementId="Clear" action="clear" icon="clear" iconClass="Clear" render="${now['buttons']['clear']}" />
<v:action labelKey="view" elementId="Read" action="read" layer="vulpeReportTable-${vulpeFormName}" render="${now['buttons']['read']}" />
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsAppend.jsp"%>
</p>