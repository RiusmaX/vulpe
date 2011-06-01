<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsPrepend.jsp"%>
<v:action validate="false" labelKey="tabularFilter" elementId="TabularFilter" action="tabularFilter" helpKey="tabularFilter" icon="filter" iconClass="TabularFilter" layerFields="vulpeTabularSelect-${targetConfigPropertyName}" config="${util:buttonConfig('tabularFilter', '')}" />
<c:set var="buttonName" value="addDetail${targetConfig.name}" />
<c:set var="buttonEL" value="${'${'}now['buttons']['addDetail${targetConfig.name}'].render${'}'}" />
<c:set var="button" value="${util:eval(pageContext, buttonEL)}" />
<c:if test="${!onlyToSee && button}">
	<v:action validate="false" labelKey="addDetail" elementId="AddDetail-${targetConfig.name}" action="addDetail" queryString="detail=${targetConfigPropertyName}" helpKey="tabularNew" icon="add" iconClass="AddDetail" config="${util:buttonConfig(buttonName, '')}" />
</c:if>
<v:action validate="false" labelKey="tabularReload" elementId="TabularReload" action="tabular" helpKey="tabularReload" icon="refresh" iconClass="TabularReload" config="${util:buttonConfig('tabularReload', '')}" />
<c:if test="${!onlyToSee}">
	<v:action validate="true" labelKey="tabularPost" elementId="TabularPost" action="tabularPost" helpKey="tabularPost" icon="save" iconClass="TabularPost" config="${util:buttonConfig('tabularPost', '')}" />
</c:if>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsAppend.jsp"%>
</p>