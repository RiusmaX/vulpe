<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsPrepend.jsp"%>
<v:action validate="false" labelKey="clear" elementId="Clear" action="clear" icon="clear" iconClass="Clear" config="${util:buttonConfig('clear', '')}" />
<v:action labelKey="view" elementId="Read" action="read" layer="vulpeReportTable-${vulpeFormName}" config="${util:buttonConfig('clear', '')}" />
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsAppend.jsp"%>
</p>