<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${empty label}"><fmt:message key="${labelKey}" var="label"/></c:if>
<c:if test="${empty help}">
<c:choose>
	<c:when test="${empty helpKey}">
		<c:choose>
			<c:when test="${not empty labelKey}"><c:set var="helpKey" value="${labelKey}"/><fmt:message key="${helpKey}" var="help"/></c:when>
			<c:otherwise><c:set var="help" value="${label}"/></c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${not empty hotKey}"><c:set var="help" value="${help} [${hotKey}]" /></c:when>
			<c:when test="${not empty accesskey}"><c:set var="help" value="${help} [Alt+Shift+${accesskey}]" /></c:when>
		</c:choose>
	</c:when>
	<c:otherwise><fmt:message key="${helpKey}" var="help"/></c:otherwise>
</c:choose>
</c:if>
<c:if test="${not empty accesskey}"><c:set var="accesskey">accesskey="${accesskey}"</c:set></c:if>
<c:if test="${not empty onclick}"><c:set var="onclick">onclick="${onclick}"</c:set></c:if>
<c:if test="${current}"><c:set var="styleClass">vulpeCurrentMenu</c:set></c:if>
<c:set var="styleClass" value="${not empty styleClass ? styleClass + ' ' : ''}vulpeMenuLink"></c:set>
<c:choose>
<c:when test="${not empty action}">
	<c:if test="${!fn:startsWith(action, '/')}"><c:set var="action" value="/${action}"/></c:if>
	<c:if test="${!fn:contains(action, '/ajax')}"><c:set var="action" value="${action}/ajax"/></c:if>
	<c:set var="onclick">onclick="${onclick}"</c:set>
	<c:set var="javascript" value="vulpe.view.request.submitLink('${action}');" />
</c:when>
<c:when test="${not empty url}"><c:set var="javascript" value="$(window.location).attr('href', '${url}');" /></c:when>
</c:choose>
<c:if test="${not empty hotKey}"><c:set var="styleClass" value="${not empty styleClass ? styleClass + ' ' : ''}hotKey[${hotKey}]"></c:set></c:if>
<c:if test="${render}">
<c:if test="${empty show}"><c:set var="show" value="${true}"/></c:if>
<c:if test="${!show}"><c:set var="style" value="display:none;${style}"/></c:if>
<li id="vulpeMenu-${elementId}" style="${style}">
	<a id="vulpeMenuLink-${elementId}" href="javascript:void(0);" class="${styleClass}" ${onclick} ${accesskey} title="${help}"><span>${label}</span></a>
	<jsp:doBody var="theBody"/>
	<c:if test="${not empty theBody}"><ul>${theBody}</ul></c:if>
</li>
<c:set var="menuLinkId" value="vulpeMenuLink-${elementId}"/>
${util:putMap(pageContext, 'vulpeMenuActions', menuLinkId, javascript, false)}
</c:if>