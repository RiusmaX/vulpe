<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>
	<c:if test="${not empty enumeration}">
		<c:set var="enumerationEL" value="${'${'}cachedEnumArray['${enumeration}']${'}'}"/>
		<c:set var="enumeration" value="${util:eval(pageContext, enumerationEL)}"/>
		<c:set var="items" value="${fn:replace(enumeration, '#{', '')}"/>
		<c:set var="items" value="${fn:replace(items, '}', '')}"/>
		<c:set var="items" value="${fn:replace(items, '\\'', '')}"/>
		<c:set var="items" value="${fn:replace(items, ' ', '')}"/>
		<c:set var="enumeration" value="#{"/>
		<c:forEach var="item" items="${fn:split(items, ',')}" varStatus="status">
			<c:if test="${status.index > 0}">
				<c:set var="enumeration" value="${enumeration},"/>
			</c:if>
			<c:set var="value" value="${fn:split(item, ':')}"/>
			<c:set var="description"><fmt:message key="${value[1]}"/></c:set>
			<c:set var="enumeration" value="${enumeration}'${value[0]}':'${description}'"/>
		</c:forEach>
		<c:set var="enumeration" value="${enumeration}}"/>
	</c:if>
	<c:if test="${empty styleClass}">
		<c:set var="styleClass" value=".vulpeNoBoarder"/>
	</c:if>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>
	<c:if test="${saveInSession}">
		<c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/>
	</c:if>
	<c:if test="${not empty property && empty value}">
		<c:set var="valueEL" value="${'${'}targetValue.${property}${'}'}"/>
		<c:set var="value" value="${util:eval(pageContext, valueEL)}"/>
	</c:if>
	<c:if test="${not empty list}">
		<c:set var="listValueCheckEL" value="${'${'}${list}${'}'}"/>
		<c:set var="listValueCheck" value="${util:eval(pageContext, listValueCheckEL)}"/>
	</c:if>
	<span id="${elementId}">
	<c:choose>
		<c:when test="${not empty enumeration || not empty listValueCheck}">
			<c:if test="${onlyToSee}">
				<c:set var="disabled" value="${onlyToSee}" />
			</c:if>
			<c:choose>
			<c:when test="${not empty enumeration}">
				<s:checkboxlist theme="simple" name="${name}" id="${elementId}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" onselect="${onselect}" cssStyle="${style}" cssClass="${styleClass}" tabindex="${tabindex}" title="${title}" list="${enumeration}" />
			</c:when>
			<c:otherwise>
				<s:checkboxlist theme="simple" name="${name}" id="${elementId}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" onselect="${onselect}" cssStyle="${style}" cssClass="${styleClass}" tabindex="${tabindex}" title="${title}" listValue="${listValue}" listKey="${listKey}" list="${list}" />
			</c:otherwise>
			</c:choose>
			<jsp:doBody/>
			<c:if test="${!onlyToSee}">
			<a id="${elementId}-Error" style="display: none;" href="javascript:;" onclick="vulpe.util.get('${elementId}').focus();" onmouseover="vulpe.view.openTooltip('${elementId}-ErrorMsg');" onmouseout="vulpe.view.closeTooltip('${elementId}-ErrorMsg');">
				<img src="${pageContext.request.contextPath}/themes/${global['theme']}/images/stop.png" border="0" />
				<div id="${elementId}-ErrorMsg" class="tooltip"><em></em></div>
			</a>
			</c:if>
		</c:when>
		<c:otherwise>
			&nbsp;
		</c:otherwise>
	</c:choose>
	</span>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
</c:if>