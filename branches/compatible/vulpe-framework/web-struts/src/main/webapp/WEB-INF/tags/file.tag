<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributes.jsp" %>
<%@ attribute name="accept" required="false" rtexprvalue="true" %>
<%@ attribute name="indexed" required="false" rtexprvalue="true" %>
<%@ attribute name="property" required="true" rtexprvalue="true" %>
<%@ attribute name="size" required="false" rtexprvalue="true" %>

<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="show" value="${true}"/>
<c:if test="${not empty logged && logged eq true && util:isLogged(pageContext) eq false}">
	<c:set var="show" value="${false}"/>
</c:if>
<c:if test="${not empty role && util:isRole(pageContext, role) eq false}">
	<c:set var="show" value="${false}"/>
</c:if>

<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>
	
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
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
</c:if>