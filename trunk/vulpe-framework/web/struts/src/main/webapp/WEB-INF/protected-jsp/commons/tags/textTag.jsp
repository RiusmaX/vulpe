<c:if test="${render}">
<%@include file="/WEB-INF/protected-jsp/commons/tags/textTagBegin.jsp" %>
<c:choose>
	<c:when test="${showAsText}"><span>${value}</span><v:hidden property="${property}"/></c:when>
	<c:otherwise><s:textfield theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" maxlength="${maxlength}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" onselect="${onselect}" readonly="${readonly}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}" /></c:otherwise>
</c:choose>
<%@include file="/WEB-INF/protected-jsp/commons/tags/textTagEnd.jsp" %>
</c:if>