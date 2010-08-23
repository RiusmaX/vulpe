<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>
	<c:if test="${empty styleClass}">
		<c:set var="styleClass" value=".vulpeNoBoarder"/>
	</c:if>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>
	<c:if test="${saveInSession}">
		<c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/>
	</c:if>
	<input type="checkbox" name="${name}" <c:if test="${not empty accesskey}"> accesskey="${accesskey}"</c:if><c:if test="${disabled}">disabled="${disabled}"</c:if><c:if test="${not empty onblur}"> onblur="${onblur}"</c:if><c:if test="${not empty onchange}"> onchange="${onchange}"</c:if><c:if test="${not empty onclick}"> onclick="${onclick}"</c:if><c:if test="${not empty ondblclick}"> ondblclick="${ondblclick}"</c:if><c:if test="${not empty onfocus}"> onfocus="${onfocus}"</c:if><c:if test="${not empty onkeydown}"> onkeydown="${onkeydown}"</c:if><c:if test="${not empty onkeypress}"> onkeypress="${onkeypress}"</c:if><c:if test="${not empty onkeyup}"> onkeyup="${onkeyup}"</c:if><c:if test="${not empty onmousedown}"> onmousedown="${onmousedown}"</c:if><c:if test="${not empty onmousemove}"> onmousemove="${onmousemove}"</c:if><c:if test="${not empty onmouseout}"> onmouseout="${onmouseout}"</c:if><c:if test="${not empty onmouseover}"> onmouseover="${onmouseover}"</c:if><c:if test="${not empty onmouseup}"> onmouseup="${onmouseup}"</c:if><c:if test="${not empty onselect}"> onselect="${onselect}"</c:if><c:if test="${readonly}"> readonly="${readonly}"</c:if><c:if test="${not empty style}"> style="${style}"</c:if><c:if test="${not empty styleClass}"> class="${styleClass}"</c:if> id="${elementId}" tabindex="${tabindex}" <c:if test="${not empty title}">title="${title}"</c:if> value="${value}" fieldValue="${fieldValue}" />
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
</c:if>