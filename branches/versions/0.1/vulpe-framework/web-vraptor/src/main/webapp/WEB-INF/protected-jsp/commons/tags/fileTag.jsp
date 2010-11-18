<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${empty show}">
	<c:set var="show" value="${true}"/>
	<c:if test="${showOnlyIfAuthenticated}"><c:set var="show" value="${util:isAuthenticated()}"/></c:if>
	<c:if test="${not empty roles}"><c:set var="show" value="${util:hasRole(roles)}"/></c:if>
</c:if>
<c:if test="${show}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagBegin.jsp" %>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>
	<s:file theme="simple" name="${name}" id="${elementId}" accesskey="${accesskey}" accept="${accept}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" tabindex="${tabindex}" title="${title}"/>
	<c:if test="${saveInSession}">
		<c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/>
	</c:if>
	<jsp:doBody/>
	<c:if test="${not empty util:getProperty(pageContext, name)}">
		<img border="0" src="${util:linkProperty(pageContext, name, 'image/jpeg', '')}"/>
	</c:if>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagEnd.jsp" %>
</c:if>