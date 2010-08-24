<c:if test="${show eq true}">
<%@include file="/WEB-INF/protected-jsp/commons/tags/textBegin.jsp" %>
<c:choose>
	<c:when test="${showAsText}">
		<s:label theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" cssStyle="${style}" cssClass="vulpeSimpleLabel" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}"/>
		<v:hidden property="${property}"/>
	</c:when>
	<c:otherwise>
		<s:textfield theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" maxlength="${maxlength}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" onselect="${onselect}" readonly="${readonly}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}" />
	</c:otherwise>
</c:choose>
<%@include file="/WEB-INF/protected-jsp/commons/tags/textEnd.jsp" %>
</c:if>