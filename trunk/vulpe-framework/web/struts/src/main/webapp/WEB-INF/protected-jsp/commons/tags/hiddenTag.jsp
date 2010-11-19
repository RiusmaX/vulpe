<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributesConfig.jsp" %>
<c:if test="${empty render}">
	<c:set var="render" value="${true}"/>
</c:if>
<c:if test="${render}">
	<s:hidden theme="simple" name="${name}" id="${elementId}" value="${value}"/>
</c:if>