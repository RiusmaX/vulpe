<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/crudActionsExtended.jsp"%>
<c:if test="${tabularFilterShow}">
<v:action validate="false" labelKey="tabularFilter" elementId="TabularFilter" action="tabularFilter" helpKey="tabularFilter" icon="filter" iconClass="TabularFilter" />
</c:if>
<c:set var="buttonEL" value="${'${'}addDetailShow${targetConfig.name}${'}'}" />
<c:set var="button" value="${util:eval(pageContext, buttonEL)}" />
<c:if test="${button}">
<v:action validate="false" labelKey="addDetail" elementId="vulpeVuttonAddDetail_${vulpeFormName}_${targetConfig.name}" action="addDetail" queryString="detail=${targetConfigPropertyName}" helpKey="tabularNew" icon="add" iconClass="AddDetail" />
</c:if>
<c:if test="${tabularReloadShow}">
<v:action validate="false" labelKey="tabularReload" elementId="TabularReload" action="tabular" helpKey="tabularReload" icon="refresh" iconClass="TabularReload" />
</c:if>
<c:if test="${tabularPostShow}">
<v:action validate="true" labelKey="tabularPost" elementId="TabularPost" action="tabularPost" helpKey="tabularPost" icon="save" iconClass="TabularPost" />
</c:if>
</p>