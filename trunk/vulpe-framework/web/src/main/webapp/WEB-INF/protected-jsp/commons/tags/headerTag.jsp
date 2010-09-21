<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${empty show}">
	<c:set var="show" value="${true}"/>
	<c:set var="springSecurity" value="${SPRING_SECURITY_CONTEXT}"/>
	<c:if test="${not empty springSecurity}">
		<c:set var="springSecurityAutentication" value="${springSecurity.authentication}"/>
	</c:if>
	<c:if test="${not empty logged && logged && (empty springSecurityAutentication || !springSecurityAutentication.authenticated)}"><c:set var="show" value="${false}"/></c:if>
	<c:if test="${not empty roles}">
		<c:set var="show" value="${false}"/>
		<c:if test="${not empty springSecurityAutentication}">
			<c:forEach var="grantedAuthority" items="${springSecurityAutentication.authorities}">
				<c:forTokens var="role" items="${roles}" delims=",">
					<c:if test="${!fn:startsWith(role, 'ROLE_')}">
						<c:set var="role" value="ROLE_${role}"/>
					</c:if>
					<c:if test="${grantedAuthority.authority == role}">
						<c:set var="show" value="${true}"/>
					</c:if>
				</c:forTokens>
			</c:forEach>
		</c:if>
	</c:if>
</c:if>