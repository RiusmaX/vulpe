<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${show}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagBegin.jsp" %>
	<c:if test="${empty autoLoad}"><c:set var="autoLoad" value="${false}"/></c:if>
	<c:if test="${empty removeEnumItems}"><c:set var="removeEnumItems" value=""/></c:if>
	<c:if test="${not empty property && empty items && !autoLoad}"><c:set var="items" value="${util:listInField(targetValue, property, removeEnumItems)}"/></c:if>
	<c:if test="${autoLoad}">
		<c:set var="itemsEL" value="${'${'}cachedClass['${items}']${'}'}"/>
		<c:set var="items" value="${util:eval(pageContext, itemsEL)}"/>
	</c:if>
	<c:if test="${empty itemLabel}"><c:set var="itemLabel" value="value"/></c:if>
	<c:if test="${empty itemKey}"><c:set var="itemKey" value="id"/></c:if>
	<c:if test="${not empty showBlank && showBlank}">
		<c:if test="${empty headerKey}"><c:set var="headerKey" value="label.vulpe.select"/></c:if>
		<fmt:message key="${headerKey}" var="headerLabel"/>
	</c:if>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}"><c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/></c:if>
	<c:choose>
	<c:when test="${showAsText}">
		<c:forEach items="${items}" var="item">
			<c:set var="keyValueEL" value="${'${'}item.${itemKey}${'}'}"/>
			<c:set var="keyValue" value="${util:eval(pageContext, keyValueEL)}"/>
			<c:set var="labelValueEL" value="${'${'}item.${itemLabel}${'}'}"/>
			<c:set var="labelValue" value="${util:eval(pageContext, labelValueEL)}"/>
			<c:if test="${value eq keyValue}"><s:label theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" cssStyle="${style}" cssClass="vulpeSimpleLabel" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${util:toString(labelValue)}"/></c:if>
		</c:forEach>
		<v:hidden property="${property}"/>
	</c:when>
	<c:otherwise>
	<select name="${name}" <c:if test="${disabled}">disabled="${disabled}" </c:if><c:if test="${multiple}">multiple="${multiple}" </c:if>onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" id="${elementId}" style="${style}" class="${styleClass}" tabindex="${tabindex}" size="${size}" title="${title}" accesskey="${accesskey}">
		<c:if test="${not empty headerLabel}"><option value="${headerValue}">${headerLabel}</option></c:if>
		<c:forEach items="${items}" var="item">
			<c:set var="keyValueEL" value="${'${'}item.${itemKey}${'}'}"/>
			<c:set var="keyValue" value="${util:eval(pageContext, keyValueEL)}"/>
			<c:set var="labelValueEL" value="${'${'}item.${itemLabel}${'}'}"/>
			<c:set var="labelValue" value="${util:eval(pageContext, labelValueEL)}"/>
			<c:choose>
				<c:when test="${value eq keyValue}"><option selected="true" value="${util:toString(keyValue)}">${util:toString(labelValue)}</option></c:when>
				<c:otherwise><option value="${util:toString(keyValue)}">${util:toString(labelValue)}</option></c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
	</c:otherwise>
	</c:choose>
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagEnd.jsp" %>
</c:if>