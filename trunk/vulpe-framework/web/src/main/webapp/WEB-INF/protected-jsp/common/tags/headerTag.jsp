<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>

<c:set var="show" value="${true}"/>
<c:if test="${not empty logged && logged eq true && util:isLogged(pageContext) eq false}">
	<c:set var="show" value="${false}"/>
</c:if>
<c:if test="${not empty role && util:isRole(pageContext, role) eq false}">
	<c:set var="show" value="${false}"/>
</c:if>