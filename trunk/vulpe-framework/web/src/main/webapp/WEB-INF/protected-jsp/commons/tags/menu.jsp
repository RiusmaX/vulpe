<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>
<c:if test="${empty helpKey}">
	<c:set var="helpKey" value="${labelKey}"/>
	<c:set var="helpKey"><fmt:message key="${labelKey}"/></c:set>
	<c:if test="${not empty accesskey}">
		<c:set var="helpKey" value="${helpKey} [Alt+Shift+${accesskey}]" />
	</c:if>
</c:if>
<c:if test="${not empty accesskey}">
	<c:set var="accesskey"> accesskey="${accesskey}"</c:set>
</c:if>
<c:if test="${not empty action}">
	<c:if test="${!fn:startsWith(action, '/')}">
		<c:set var="action" value="/${action}"/>
	</c:if>
	<c:if test="${!fn:contains(action, '/ajax')}">
		<c:set var="action" value="${action}/ajax"/>
	</c:if>
	<c:set var="onclick"> onclick="vulpe.view.request.submitMenu('${action}');"</c:set>
</c:if>
<li>
	<a href="javascript:void(0);" class="current"${onclick}${accesskey} title="${helpKey}"><span><fmt:message key="${labelKey}" /></span></a>
	<ul>
		<jsp:doBody/>
	</ul>
</li>