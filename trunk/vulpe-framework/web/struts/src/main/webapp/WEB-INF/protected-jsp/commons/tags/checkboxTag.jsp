<c:if test="${render}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagBegin.jsp" %>
	<c:if test="${empty styleClass}"><c:set var="styleClass" value=".vulpeNoBorder"/></c:if>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>
	<c:if test="${saveInSession}"><c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/></c:if>
	<c:if test="${showAsText}"><c:set var="disabled" value="true"/></c:if>
	<s:checkbox theme="simple" name="${name}" id="${elementId}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" onselect="${onselect}" cssStyle="${style}" cssClass="${styleClass}" tabindex="${tabindex}" title="${title}" value="${value}" fieldValue="${fieldValue}" />
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagEnd.jsp" %>
</c:if>