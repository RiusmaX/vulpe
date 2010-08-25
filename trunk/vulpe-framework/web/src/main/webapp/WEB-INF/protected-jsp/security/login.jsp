<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<form id="vulpeLoginForm" action="${pageContext.request.contextPath}/j_spring_security_check" method="POST">
<div id="contentTitle"><fmt:message key="vulpe.security.login.title.application"/></div>
<div id="content">
	<c:if test="${not empty param.loginError}">
	<p style="color:red">
	<fmt:message key="vulpe.security.error.login.failed" /> <c:choose>
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
	</c:choose>
	</p>
	</c:if>
	<v:text elementId="j_username" name="j_username" targetName="" maxlength="20" labelKey="label.vulpe.security.login.username" lowerCase="true" value="${not empty param.loginError ? SPRING_SECURITY_LAST_USERNAME : ''}" style="width: 150px;" />
	<v:password elementId="j_password" name="j_password" targetName="" maxlength="20" labelKey="label.vulpe.security.login.password" style="width: 150px;" />
	<p>
		<input name="submit" type="submit" id="buttonSubmitLoginForm" value="<fmt:message key='label.vulpe.security.login'/>" class="vulpeButton" style="width: 75px;">&nbsp;<input name="reset"type="reset" value="<fmt:message key='label.vulpe.security.login.clear'/>" class="vulpeButton" style="width: 75px;">
	</p>
<script type="text/javascript">
$(document).ready(function() {
	vulpe.config.authenticator.url.redirect = "${SPRING_SECURITY_SAVED_REQUEST_KEY.redirectUrl}";
	if (vulpe.config.authenticator.url.redirect == "") {
		vulpe.config.authenticator.url.redirect = "${pageContext.request.contextPath}/index.jsp";
	}
	/*
	vulpe.util.get('j_username').focus(function() {
		$(this).effect("highlight");
	});
	vulpe.util.get('j_password').focus(function() {
		$(this).effect("highlight");
	});
	*/
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
</form>