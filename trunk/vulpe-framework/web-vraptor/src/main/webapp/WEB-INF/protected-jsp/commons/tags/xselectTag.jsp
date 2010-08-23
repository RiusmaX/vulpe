<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>
	<c:if test="${not empty enumeration}">
		<c:set var="enumerationEL" value="${'${'}vulpeCachedEnumArray['${enumeration}']${'}'}"/>
		<c:set var="enumeration" value="${util:eval(pageContext, enumerationEL)}"/>
	</c:if>
	<c:if test="${empty size && not empty maxlength}">
		<c:set var="size" value="${maxlength}"/>
	</c:if>
	<c:if test="${empty showBlank}">
		<c:set var="showBlank" value="${false}"/>
	</c:if>
	<c:if test="${showBlank}">
		<c:if test="${empty headerKey}">
			<c:set var="headerKey" value="label.vulpe.select"/>
		</c:if>
		<fmt:message key="${headerKey}" var="headerValue"/>
	</c:if>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>
	<c:choose>
		<c:when test="${showAsText}">
			<s:label theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" cssStyle="${style}" cssClass="vulpeSimpleLabel ${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}"/>
			<v:hidden property="${property}"/>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${not empty enumeration}">
					<c:choose>
						<c:when test="${showBlank}">
							<s:select list="${enumeration}" headerKey="${headerKey}" headerValue="${headerValue}" theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}" onselect="${onselect}" emptyOption="${emptyOption}" />
						</c:when>
						<c:otherwise>
							<s:select list="${enumeration}" theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}" onselect="${onselect}" emptyOption="${emptyOption}" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${isEnum}">
					<c:choose>
						<c:when test="${showBlank}">
							<s:select list="${list}" headerKey="${headerKey}" headerValue="${headerValue}" theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}" onselect="${onselect}" emptyOption="${emptyOption}" />
						</c:when>
						<c:otherwise>
							<s:select list="${list}" theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}" onselect="${onselect}" emptyOption="${emptyOption}" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${showBlank}">
							<s:select list="${list}" listKey="${listKey}" listValue="${listValue}" headerKey="${headerKey}" headerValue="${headerValue}" theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}" onselect="${onselect}" emptyOption="${emptyOption}" />
						</c:when>
						<c:otherwise>
							<s:select list="${list}" listKey="${listKey}" listValue="${listValue}" theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}" onselect="${onselect}" emptyOption="${emptyOption}" />
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
</c:if>