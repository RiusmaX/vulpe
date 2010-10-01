<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>
<c:if test="${not empty global['showAsMobile'] || global['breakLabel']}"><c:set var="breakLabel" value="${true}"/></c:if>
<c:if test="${empty currentItem}">
	<c:if test="${empty targetName}">
		<c:if test="${empty targetConfig}">
			<c:set var="targetName" value="${not empty vulpeTargetName ? vulpeTargetName : 'entity'}"/>
		</c:if>
		<c:if test="${not empty targetConfig}">
			<c:set var="targetName" value="${targetConfigPropertyName}[${currentStatus.index}]"/>
		</c:if>
	</c:if>
	<c:if test="${empty targetValue}">
		<c:choose>
		<c:when test="${empty targetConfig}">
			<c:set var="targetValueEL" value="${'${'}${targetName}${'}'}"/>
			<c:set var="targetValue" value="${util:eval(pageContext, targetValueEL)}"/>
		</c:when>
		<c:otherwise>
			<c:set var="targetValue" value="${currentItem}"/>
			<c:if test="${empty targetValue && not empty targetName}">
				<c:set var="targetValueEL" value="${'${'}${targetName}${'}'}"/>
				<c:set var="targetValue" value="${util:eval(pageContext, targetValueEL)}"/>
			</c:if>
		</c:otherwise>
		</c:choose>
	</c:if>
</c:if>
<c:set var="propertyTarget" value="${empty targetValue ? currentItem : targetValue}"/>
<c:set var="valueEL" value="${'${'}propertyTarget.${property}${'}'}"/>
<c:set var="value" value="${util:eval(pageContext, valueEL)}"/>
<c:if test="${not empty paragraph && paragraph}"><p></c:if>
<c:if test="${not empty labelKey}">
	<v:label key="${labelKey}" breakLine="${breakLabel}"/>
</c:if>
<c:if test="${not empty styleClass}"><c:set var="styleClass"> class="${styleClass}"</c:set></c:if>
<c:if test="${not empty style}"><c:set var="style"> style="${style}"</c:set></c:if>
<span id="${property}${not empty currentStatus ? currentStatus.count : ''}-show" ${styleClass}${style}>
<c:choose>
	<c:when test="${not empty type}">
		<c:choose>
			<c:when test="${type == 'enum' || type == 'ENUM' || type == 'Enum'}">
				<c:if test="${not empty value}">
				${util:enumInField(propertyTarget, property, value)}
				</c:if>
			</c:when>
			<c:when test="${type == 'date' || type == 'DATE' || type == 'Date'}">
				<c:if test="${not empty value}">
				<fmt:formatDate value="${value}" pattern="${pattern}"/>
				</c:if>
			</c:when>
		</c:choose>
	</c:when>
	<c:otherwise>${value}</c:otherwise>
</c:choose>
</span>
<c:if test="${not empty paragraph && paragraph}"></p></c:if>