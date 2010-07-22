<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributes.jsp" %>
<%@ attribute name="onselect" required="false" rtexprvalue="true" %>
<%@ attribute name="property" required="false" rtexprvalue="true" %>
<%@ attribute name="readonly" required="false" rtexprvalue="true" %>
<%@ attribute name="size" required="false" rtexprvalue="true" %>
<%@ attribute name="showPassword" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="maxlength" required="false" rtexprvalue="true" %>

<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>

<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>
	
	<c:if test="${empty size && not empty maxlength}">
		<c:set var="size" value="${maxlength}"/>
	</c:if>
	
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>
	
	<c:if test="${saveInSession}">
		<c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/>
	</c:if>
	
	<s:password theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" maxlength="${maxlength}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" onselect="${onselect}" id="${elementId}" readonly="${readonly}" cssStyle="${style}" cssClass="${styleClass}" size="${size}" tabindex="${tabindex}" title="${title}" value="${value}" showPassword="${showPassword}"/>
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
</c:if>