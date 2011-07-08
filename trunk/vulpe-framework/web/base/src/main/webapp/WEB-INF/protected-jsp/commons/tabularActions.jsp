<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsPrepend.jsp"%>
<v:action validate="false" labelKey="tabularFilter" elementId="TabularFilter" action="tabularFilter" helpKey="tabularFilter" icon="filter" iconClass="TabularFilter" layerFields="vulpeTabularSelect-${now['now['targetConfig']PropertyName']}" config="${util:buttonConfig(pageContext, 'tabularFilter', '')}" />
<c:set var="buttonName" value="addDetail${now['targetConfig'].name}" />
<c:if test="${!onlyToSee && util:isButtonRender(pageContext, buttonName, '')}">
	<v:action validate="false" labelKey="addDetail" elementId="AddDetail-${now['targetConfig'].name}" action="addDetail" queryString="detail=${now['now['targetConfig']PropertyName']}" helpKey="tabularNew" icon="add" iconClass="AddDetail" config="${util:buttonConfig(pageContext, buttonName, '')}" />
</c:if>
<v:action validate="false" labelKey="tabularReload" elementId="TabularReload" action="tabular" helpKey="tabularReload" icon="refresh" iconClass="TabularReload" config="${util:buttonConfig(pageContext, 'tabularReload', '')}" />
<c:if test="${!onlyToSee}">
	<v:action validate="true" labelKey="tabularPost" elementId="TabularPost" action="tabularPost" helpKey="tabularPost" icon="save" iconClass="TabularPost" config="${util:buttonConfig(pageContext, 'tabularPost', '')}" />
</c:if>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsAppend.jsp"%>
</p>