<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${empty show}">
	<c:set var="show" value="${true}"/>
	<c:if test="${not empty logged && logged && !securityContext.authenticated}"><c:set var="show" value="${false}"/></c:if>
	<c:if test="${not empty role}">
		<c:set var="show" value="${false}"/>
		<c:forEach var="userRole" items="${securityContext.userRoles}">
			<c:if test="${userRole == role}">
				<c:set var="show" value="${true}"/>
			</c:if>
		</c:forEach>
	</c:if>
</c:if>