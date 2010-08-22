<c:if test="${empty saveInSession}"><c:set var="saveInSession" value="${false}"/></c:if>
<c:if test="${empty expireInSession}"><c:set var="expireInSession" value="${false}"/></c:if>
<c:if test="${empty paragraph}"><c:set var="paragraph" value="true"/></c:if>
<c:if test="${empty style}"><c:set var="style" value=""/></c:if>
<c:set var="styleClass" value="${styleClass} focused"/>
<c:if test="${required}"><c:set var="styleClass" value="${styleClass} vulpeRequired"/></c:if>
<c:if test="${not empty global['showAsMobile'] || global['breakLabel']}"><c:set var="breakLabel" value="${true}"/></c:if>
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
<c:if test="${not empty property && empty name}"><c:set var="name" value="${targetName}.${property}"/></c:if>
<c:if test="${empty elementId}">
	<c:set var="prepareName" value="${fn:replace(name, '[', ':')}"/>
	<c:set var="prepareName" value="${fn:replace(prepareName, '].', ':')}"/>
	<c:set var="elementId" value="${vulpeFormName}_${prepareName}"/>
</c:if>
<c:if test="${not empty property && empty value}">
	<c:set var="valueEL" value="${'${'}targetValue.${property}${'}'}"/>
	<c:set var="value" value="${util:eval(pageContext, valueEL)}"/>
</c:if>
<c:if test="${paragraph}"><p></c:if>
<c:if test="${not empty labelKey}">
	<fmt:message key="${labelKey}" var="label"/>
	<c:if test="${empty titleKey}">
		<c:set var="title" value="${label}"/>
	</c:if>
	<c:if test="${empty labelClass}">
		<c:set var="labelClass" value="vulpeBlockLabel"/>
	</c:if>
	<label id="${elementId}_label" for="${elementId}" style="${labelStyle}" class="${labelClass}">${label}</label>
	<c:if test="${breakLabel}">
		<br/>
	</c:if>
</c:if>
<c:if test="${saveInSession}"><c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/></c:if>
<c:if test="${not empty value}"><c:set var="value" value="${util:toString(value)}"/></c:if>
<c:if test="${onlyToSee}">	<c:set var="showAsText" value="${true}"/></c:if>