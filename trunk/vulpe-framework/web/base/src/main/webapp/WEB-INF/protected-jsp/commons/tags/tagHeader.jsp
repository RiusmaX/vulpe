<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${empty render}">
	<c:set var="render" value="${true}"/>
	<c:if test="${showOnlyIfAuthenticated}"><c:set var="render" value="${util:isAuthenticated(pageContext)}"/></c:if>
	<c:if test="${not empty roles}"><c:set var="render" value="${util:hasRoles(pageContext, roles)}"/></c:if>
</c:if>