<c:if test="${render}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagBegin.jsp" %>
	<c:if test="${not empty enumeration}">
		<c:set var="enumerationEL" value="${'${'}cachedEnumArray['${enumeration}']${'}'}"/>
		<c:set var="enumeration" value="${util:eval(pageContext, enumerationEL)}"/>
		<c:set var="items" value="${fn:replace(enumeration, '#{', '')}"/>
		<c:set var="items" value="${fn:replace(items, '}', '')}"/>
		<c:set var="items" value="${fn:replace(items, '\\'', '')}"/>
		<c:set var="items" value="${fn:replace(items, ' ', '')}"/>
		<c:set var="enumeration" value="#{"/>
		<c:forEach var="item" items="${fn:split(items, ',')}" varStatus="status">
			<c:if test="${status.index > 0}"><c:set var="enumeration" value="${enumeration},"/></c:if>
			<c:set var="value" value="${fn:split(item, ':')}"/>
			<c:set var="description"><fmt:message key="${value[1]}"/></c:set>
			<c:set var="enumeration" value="${enumeration}'${value[0]}':'${description}'"/>
		</c:forEach>
		<c:set var="enumeration" value="${enumeration}}"/>
	</c:if>
	<c:if test="${empty styleClass}"><c:set var="styleClass" value=".vulpeNoBorder"/></c:if>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}"><c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/></c:if>
	<c:if test="${saveInSession}"><c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/></c:if>
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
			<c:if test="${onlyToSee}"><c:set var="disabled" value="${onlyToSee}" /></c:if>
			<c:choose>
				<c:when test="${not empty enumeration}"><s:checkboxlist theme="simple" name="${name}" id="${elementId}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" onselect="${onselect}" cssStyle="${style}" cssClass="${styleClass}" tabindex="${tabindex}" title="${title}" list="${enumeration}" /></c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${not empty detail && detail}">
							<c:set var="propertyValueEL" value="${'${'}entity.${property}${'}'}"/>
							<c:set var="propertyValue" value="${util:eval(pageContext, propertyValueEL)}"/>
							<c:choose>
								<c:when test="${not empty detailProperty}"><c:set var="detailProperty" value="${detailProperty}.${listKey}"/></c:when>
								<c:otherwise><c:set var="detailProperty" value="${listKey}"/></c:otherwise>
							</c:choose>
							<c:forEach var="item" items="${listValueCheck}" varStatus="itemStatus">
							<c:forEach var="detailItem" items="${propertyValue}" varStatus="detailStatus">
								<c:set var="detailItemId" value="${detailItem.id}"/>
								<c:if test="${not empty detailProperty}">
									<c:set var="detailItemIdEL" value="${'${'}detailItem.${detailProperty}${'}'}"/>
									<c:set var="detailItemId" value="${util:eval(pageContext, detailItemIdEL)}"/>
								</c:if>
								<c:if test="${item.id == detailItemId}">
								<c:set var="itemName" value="entity.${property}[${itemStatus.index}]"/>
								<c:set var="itemId" value="${fn:replace(itemName, '[', '__')}"/>
								<c:set var="itemId" value="${fn:replace(itemId, ']', '__')}"/>
								<c:set var="itemId" value="${fn:replace(itemId, '.', '_')}"/>
								<c:if test="${not empty detailProperty}"><input id="${itemId}id" type="hidden" value="${detailItem.id}" name="${itemName}.id"></c:if>
								<input id="${itemId}selected" type="hidden" value="false" name="${itemName}.selected">
								</c:if>
							</c:forEach>
							</c:forEach>
							<c:forEach var="item" items="${listValueCheck}" varStatus="itemStatus">
								<c:set var="checked" value=""/>
								<c:forEach var="detailItem" items="${propertyValue}">
									<c:set var="detailItemId" value="${detailItem.id}"/>
									<c:if test="${not empty detailProperty}">
										<c:set var="detailItemIdEL" value="${'${'}detailItem.${detailProperty}${'}'}"/>
										<c:set var="detailItemId" value="${util:eval(pageContext, detailItemIdEL)}"/>
									</c:if>
									<c:if test="${item.id == detailItemId}"><c:set var="checked">checked="checked"</c:set></c:if>
								</c:forEach>
								<c:set var="itemLabelEL" value="${'${'}item.${listValue}${'}'}"/>
								<c:set var="itemLabel" value="${util:eval(pageContext, itemLabelEL)}"/>
								<c:set var="itemPrepareName" value="entity.${property}[${itemStatus.index}]"/>
								<c:set var="itemPrepareId" value="${fn:replace(itemPrepareName, '[', '__')}"/>
								<c:set var="itemPrepareId" value="${fn:replace(itemPrepareId, ']', '__')}"/>
								<c:set var="itemPrepareId" value="${fn:replace(itemPrepareId, '.', '_')}"/>
								<c:set var="itemName" value="entity.${property}[${itemStatus.index}].${detailProperty}"/>
								<c:set var="itemId" value="${fn:replace(itemName, '[', '__')}"/>
								<c:set var="itemId" value="${fn:replace(itemId, '].', '__')}"/>
								<c:set var="itemId" value="${fn:replace(itemId, '.', '_')}"/>
								<input id="${itemId}" type="checkbox" ${checked} value="${item.id}" name="entity.${property}[${itemStatus.index}].${detailProperty}" onclick="vulpe.view.controlDetailChecked(this.checked, '${itemPrepareId}')">&nbsp;<label for="${itemId}" style="display: inline;">${itemLabel}</label><c:if test="${breakLabel}"><br/></c:if>
							</c:forEach>
						</c:when>
						<c:otherwise><s:checkboxlist theme="simple" name="${name}" id="${elementId}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" onselect="${onselect}" cssStyle="${style}" cssClass="${styleClass}" tabindex="${tabindex}" title="${title}" listValue="${listValue}" listKey="${listKey}" list="${list}" /></c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<jsp:doBody/>
		</c:when>
		<c:otherwise>&nbsp;</c:otherwise>
	</c:choose>
	</span>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagEnd.jsp" %>
</c:if>