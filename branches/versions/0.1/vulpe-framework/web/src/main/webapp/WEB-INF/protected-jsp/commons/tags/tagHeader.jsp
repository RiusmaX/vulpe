<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${empty show}">
	<c:set var="show" value="${true}"/>
	<c:if test="${showOnlyIfAuthenticated}"><c:set var="show" value="${util:isAuthenticated()}"/></c:if>
	<c:if test="${not empty roles}"><c:set var="show" value="${util:hasRole(roles)}"/></c:if>
</c:if>