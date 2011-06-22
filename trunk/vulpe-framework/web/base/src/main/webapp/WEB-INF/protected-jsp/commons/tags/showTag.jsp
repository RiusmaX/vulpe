<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${not empty global['project-mobile-enabled'] || global['project-view-breakLabel']}"><c:set var="breakLabel" value="${true}"/></c:if>
<c:if test="${empty currentItem}">
	<c:if test="${empty targetName}">
		<c:if test="${empty targetConfig}"><c:set var="targetName" value="${not empty vulpeTargetName ? vulpeTargetName : 'entity'}"/></c:if>
		<c:if test="${not empty targetConfig}"><c:set var="targetName" value="${targetConfigPropertyName}[${currentStatus.index}]"/></c:if>
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
<c:set var="elementId" value="${property}${not empty currentStatus ? currentStatus.count : ''}-show"/>
<c:if test="${not empty paragraph && paragraph}"><p class="vulpeField"></c:if>
<c:if test="${not empty labelKey}"><v:label key="${labelKey}"/></c:if>
<c:if test="${empty styleClass}"><c:set var="styleClass" value="vulpeShowAsText"/></c:if>
<c:if test="${not empty styleClass}"><c:set var="styleClass"> class="${styleClass}"</c:set></c:if>
<c:if test="${not empty style}"><c:set var="style"> style="${style}"</c:set></c:if>
<span id="${elementId}" ${styleClass}${style}>
<c:choose>
	<c:when test="${not empty type}">
		<c:choose>
			<c:when test="${type == 'enum' || type == 'ENUM' || type == 'Enum'}">
				<c:if test="${not empty value}">${util:enumInField(propertyTarget, property, value)}</c:if>
			</c:when>
			<c:when test="${type == 'date' || type == 'DATE' || type == 'Date'}">
				<c:if test="${not empty value}"><fmt:formatDate value="${value}" pattern="${pattern}"/></c:if>
			</c:when>
		</c:choose>
	</c:when>
	<c:otherwise>
	<c:choose>
	<c:when test="${not empty booleanTo}">${util:booleanTo(value, booleanTo)}</c:when>
	<c:otherwise>
		<c:choose>
		<c:when test="${not empty limitContent && limitContent > 0 && fn:length(value) > limitContent}">
			<c:set var="fullValue" value="${value}"/>
			<c:if test="${fn:length(value) > limitContent}">
				<c:set var="value" value="${fn:substring(value, 0, limitContent)}..."/>
			</c:if>
			<span id="${elementId}_value">${util:toString(value)}&nbsp;</span><span id="${elementId}_showContent" class="vulpeShowContent"><a href="javascript:void(0);" onclick="vulpe.view.showContent('${elementId}');"><fmt:message key="vulpe.messages.showContent"/></a></span>
			<div id="${elementId}_content" class="vulpeContentOverflow" style="display: none">${fullValue}<div id="${elementId}-closeContent" class="vulpeCloseContentOverflow"><a href="javascript:void(0);" onclick="vulpe.view.hideContent('${elementId}');"><fmt:message key="vulpe.messages.close"/></a></div></div>
		</c:when>
		<c:otherwise>${util:toString(value)}</c:otherwise>
		</c:choose>
	</c:otherwise>
	</c:choose>
	</c:otherwise>
</c:choose>
</span>
<c:if test="${not empty paragraph && paragraph}"></p></c:if>