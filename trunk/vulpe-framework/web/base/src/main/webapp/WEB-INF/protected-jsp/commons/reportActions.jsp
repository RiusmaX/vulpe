<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsPrepend.jsp"%>
<v:action validate="false" labelKey="clear" elementId="Prepare" action="prepare" show="${now['buttons']['prepare']}"/>
<v:action labelKey="clear" elementId="Clear" javascript="document.forms['${vulpeFormName}'].reset();" show="${now['buttons']['clear']}"/>
<v:action labelKey="view" elementId="Read" action="read" layer="vulpeReportTable-${vulpeFormName}" show="${now['buttons']['read']}" />
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsAppend.jsp"%>
</p>