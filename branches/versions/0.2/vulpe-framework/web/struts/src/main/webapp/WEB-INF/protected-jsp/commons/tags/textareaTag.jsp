<c:if test="${render}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagBegin.jsp" %>
	<c:choose>
		<c:when test="${showAsText}">
			<s:label theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" cssStyle="${style}" cssClass="vulpeSimpleLabel" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}"/>
			<v:hidden property="${property}"/>
		</c:when>
		<c:otherwise>
			<s:textarea theme="simple" name="${name}" accesskey="${accesskey}" cols="${cols}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" id="${elementId}" readonly="${readonly}" rows="${rows}" cssStyle="${style}" cssClass="${styleClass}" tabindex="${tabindex}" title="${title}" value="${value}"/>
		</c:otherwise>
	</c:choose>
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagEnd.jsp" %>
</c:if>