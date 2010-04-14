<%@include file="/WEB-INF/protected-jsp/common/common.jsp" %>

<c:set var="show" value="${true}"/>
<c:if test="${not empty logged && logged eq true && util:isLogged() eq false}">
	<c:set var="show" value="${false}"/>
</c:if>
<c:if test="${not empty role && util:isRole(role) eq false}">
	<c:set var="show" value="${false}"/>
</c:if>