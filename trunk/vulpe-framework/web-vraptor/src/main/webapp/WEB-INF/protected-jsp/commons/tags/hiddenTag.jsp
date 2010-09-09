<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/configAttributesTag.jsp" %>
<c:if test="${empty render}">
	<c:set var="render" value="${true}"/>
</c:if>
<c:if test="${render}">
	<input type="hidden" name="${name}" id="${elementId}" value="${value}"/>
</c:if>