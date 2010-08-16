<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<form action="${pageContext.request.contextPath}/j_spring_security_check" method="POST"
	id="vulpeLoginForm">
<div id="contentTitle"><fmt:message>vulpe.security.login.title.application</fmt:message></div>
<div id="content"><c:if
	test="${not empty param.loginError}">
	<p><font color="red"><fmt:message key="vulpe.security.error.login.failed" /> <c:choose>
		<c:when test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION.cause, 'UserNotFound')}">
			<fmt:message key="vulpe.security.error.user.not.found">
				<fmt:param value="${SPRING_SECURITY_LAST_USERNAME}" />
			</fmt:message>
		</c:when>
		<c:when test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION.cause, 'InvalidPassword')}">
			<fmt:message key="vulpe.security.error.invalid.password" />
		</c:when>
		<c:when test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION.cause, 'InactiveUser')}">
			<fmt:message key="vulpe.security.error.invulpe.user">
				<fmt:param value="${SPRING_SECURITY_LAST_USERNAME}" />
			</fmt:message>
		</c:when>
	</c:choose></font></p>
</c:if> <v:text elementId="j_username" name="j_username" targetName=""
	labelKey="label.vulpe.security.login.username"
	value="${not empty param.loginError ? SPRING_SECURITY_LAST_USERNAME : ''}" /> <v:password
	elementId="j_password" name="j_password" targetName=""
	labelKey="label.vulpe.security.login.password" />
<p><input name="submit" type="submit" id="buttonSubmitLoginForm"
	value="<fmt:message key='label.vulpe.security.login'/>">&nbsp; <input name="reset"
	type="reset" value="<fmt:message key='label.vulpe.security.login.clear'/>"></p>
</form>
<script type="text/javascript">
	$(document).ready(function() {
		<c:choose>
		<c:when test="${not empty vulpeLogoutExecuted && vulpeLogoutExecuted}">
		vulpe.config.authenticator.url.redirect = "${fn:replace(SPRING_SECURITY_SAVED_REQUEST_KEY.redirectUrl, '/ajax', '')}";
		</c:when>
		<c:otherwise>
		vulpe.config.authenticator.url.redirect = "${SPRING_SECURITY_SAVED_REQUEST_KEY.redirectUrl}";
		if (vulpe.config.authenticator.url.redirect == "") {
			vulpe.config.authenticator.url.redirect = "${pageContext.request.contextPath}/index.jsp";
		}
		</c:otherwise>
		</c:choose>
		vulpe.util.get('j_username').focus(function() {
			$(this).effect("highlight");
		});
		vulpe.util.get('j_password').focus(function() {
			$(this).effect("highlight");
		});
		var j_username = vulpe.util.getElement('j_username');
		var j_password = vulpe.util.getElement('j_password');
		if (j_username.value == '') {
			j_username.focus();
		} else if (j_password.value == '') {
			j_password.focus();
		}

		vulpe.util.get('buttonSubmitLoginForm').click(function() {
			vulpe.view.request.submitLoginForm('vulpeLoginForm', 'vulpeLoginForm', '', 'body', false, '', '');
			return false;
		});
	});
</script>
</div>