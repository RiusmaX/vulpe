<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>
<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>
	<c:if test="${empty itemLabel}"><c:set var="itemLabel" value="value"/></c:if>
	<c:if test="${empty itemKey}"><c:set var="itemKey" value="id"/></c:if>
	<c:set var="itemKeyEL" value="${'${'}item.${itemKey}${'}'}"/>
	<c:set var="itemLabelEL" value="${'${'}item.${itemLabel}${'}'}"/>
	<c:if test="${not empty property && empty items}"><c:set var="items" value="${util:listInField(targetValue, property)}"/></c:if>
	<c:if test="${empty styleClass}"><c:set var="styleClass" value=".vulpeNoBoarder"/></c:if>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}"><c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/></c:if>
	<c:if test="${saveInSession}"><c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/></c:if>
	<c:forEach items="${items}" var="item" varStatus="i">
		<c:set var="keyValueEL" value="${'${'}item.${itemKey}${'}'}"/>
		<c:set var="keyValue" value="${util:eval(pageContext, keyValueEL)}"/>
		<c:set var="labelValueEL" value="${'${'}item.${itemLabel}${'}'}"/>
		<c:set var="labelValue" value="${util:eval(pageContext, labelValueEL)}"/>
		<c:choose>
			<c:when test="${value eq keyValue}">
				<input type="radio" checked="true" value="${util:toString(keyValue)}" name="${name}" id="${elementId}_${i.index}" accesskey="${accesskey}" <c:if test="${disabled}">disabled="${disabled}" </c:if>onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" style="${style}" class="${styleClass}" tabindex="${tabindex}" title="${title}"/><label for="${elementId}_${i.index}">${util:toString(labelValue)}</label>
			</c:when>
			<c:otherwise>
				<input type="radio" value="${util:toString(keyValue)}" name="${name}" id="${elementId}_${i.index}" accesskey="${accesskey}" <c:if test="${disabled}">disabled="${disabled}" </c:if>onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" style="${style}" class="${styleClass}" tabindex="${tabindex}" title="${title}"/><label for="${elementId}_${i.index}">${util:toString(labelValue)}</label>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
</c:if>