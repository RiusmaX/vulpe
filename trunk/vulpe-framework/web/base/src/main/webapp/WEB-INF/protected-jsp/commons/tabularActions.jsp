<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsPrepend.jsp"%>
<c:if test="${now['buttons']['tabularFilter']}"><v:action validate="false" labelKey="tabularFilter" elementId="TabularFilter" action="tabularFilter" helpKey="tabularFilter" icon="filter" iconClass="TabularFilter" /></c:if>
<c:set var="buttonEL" value="${'${'}now['buttons']['addDetail${targetConfig.name}']${'}'}" />
<c:set var="button" value="${util:eval(pageContext, buttonEL)}" />
<v:action validate="false" labelKey="addDetail" elementId="AddDetail-${targetConfig.name}" action="addDetail" queryString="detail=${targetConfigPropertyName}" helpKey="tabularNew" icon="add" iconClass="AddDetail" show="${!onlyToSee && button}" />
<v:action validate="false" labelKey="tabularReload" elementId="TabularReload" action="tabular" helpKey="tabularReload" icon="refresh" iconClass="TabularReload" show="${now['buttons']['tabularReload']}" />
<v:action validate="true" labelKey="tabularPost" elementId="TabularPost" action="tabularPost" helpKey="tabularPost" icon="save" iconClass="TabularPost" show="${!onlyToSee && now['buttons']['tabularPost']}" />
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsAppend.jsp"%>
</p>