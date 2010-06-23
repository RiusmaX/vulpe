<c:if test="${empty tabindex}">
	<c:set var="tabindex" value="${TABINDEX}"/>
</c:if>

<c:set var="title" value=""/>
<c:if test="${not empty titleKey}">
	<fmt:message key="${titleKey}" var="title"/>
</c:if>

<%@include file="/WEB-INF/protected-jsp/commons/tags/configAttributesTag.jsp" %>