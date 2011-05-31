<c:if test="${not empty titleKey}"><fmt:message key="${titleKey}" var="title"/></c:if>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributesConfig.jsp" %>
<c:if test="${not empty style}"><c:set var="pStyle">style="${style}"</c:set></c:if>
<c:set var="paragraphId" value="${elementId}-paragraph" scope="request"/>
<c:if test="${paragraph}"><p id="${paragraphId}" ${pStyle}></c:if>
<c:if test="${not empty labelKey}">
	<fmt:message key="${labelKey}" var="label"/>
	<c:if test="${empty titleKey}"><c:set var="title" value="${label}"/></c:if>
	<c:if test="${empty labelClass}"><c:set var="labelClass" value="vulpeBlockLabel"/></c:if>
	<label id="${elementId}-label" for="${elementId}" style="${labelStyle}" class="${labelClass}">${label}</label>
	<c:if test="${breakLabel}"><br/></c:if>
</c:if>
<c:set var="fieldId" value="${elementId}-field" scope="request"/>
<span id="${fieldId}" class="vulpeField">