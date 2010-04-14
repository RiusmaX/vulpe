<%@include file="/WEB-INF/protected-jsp/common/tags/tagAttributes.jsp" %>
<%@ attribute name="onselect" required="false" rtexprvalue="true" %>
<%@ attribute name="property" required="false" rtexprvalue="true" %>
<%@ attribute name="indexed" required="false" rtexprvalue="true" %>
<%@ attribute name="fieldValue" required="false" rtexprvalue="true" type="java.lang.Object" %>

<%@include file="/WEB-INF/protected-jsp/common/tags/headerTag.jsp" %>

<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/common/tags/beginTag.jsp" %>

	<c:if test="${empty styleClass}">
		<c:set var="styleClass" value="noBorder"/>
	</c:if>
	
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>
	
	<c:if test="${saveInSession}">
		<c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/>
	</c:if>
	
	<s:checkbox theme="simple" name="${name}" id="${elementId}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" onselect="${onselect}" cssStyle="${style}" cssClass="${styleClass}" tabindex="${tabindex}" title="${title}" value="${value}" fieldValue="${fieldValue}" />
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/common/tags/endTag.jsp" %>
</c:if>