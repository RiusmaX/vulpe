<c:if test="${empty tabindex}"><c:set var="tabindex" value="${TABINDEX}"/></c:if>
<c:if test="${not empty titleKey}"><fmt:message key="${titleKey}" var="title"/></c:if>
<%@include file="/WEB-INF/protected-jsp/commons/tags/configAttributesTag.jsp" %>
<c:if test="${paragraph}"><p></c:if>
<c:if test="${not empty labelKey}">
	<fmt:message key="${labelKey}" var="label"/>
	<c:if test="${empty titleKey}">
		<c:set var="title" value="${label}"/>
	</c:if>
	<c:if test="${empty labelClass}">
		<c:set var="labelClass" value="vulpeBlockLabel"/>
	</c:if>
	<label id="${elementId}-label" for="${elementId}" style="${labelStyle}" class="${labelClass}">${label}</label>
	<c:if test="${breakLabel}">
		<br/>
	</c:if>
</c:if>