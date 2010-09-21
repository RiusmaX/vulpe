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
<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>
	<s:file theme="simple" name="${name}" id="${elementId}" accesskey="${accesskey}" accept="${accept}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" tabindex="${tabindex}" title="${title}"/>
	<c:if test="${saveInSession}">
		<c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/>
	</c:if>
	<jsp:doBody/>
	<c:if test="${not empty util:getProperty(pageContext, name)}">
		<img border="0" src="${util:linkProperty(pageContext, name, 'image/jpeg', '')}"/>
	</c:if>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
</c:if>