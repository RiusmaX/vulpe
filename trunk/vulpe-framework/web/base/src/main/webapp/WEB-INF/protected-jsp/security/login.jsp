<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<form id="vulpeLoginForm" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
<div id="content">
	<div id="title"><fmt:message key="vulpe.security.login.title.application"/></div>
	<c:if test="${not empty param.loginError}">
	<p style="color:red">
	<fmt:message key="vulpe.security.error.login.failed" /> <c:choose>
		<c:when test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION.cause, 'UserNotFound')}">
			<fmt:message key="vulpe.security.error.user.not.found">
				<fmt:param value="${vulpeUserAuthentication}" />
			</fmt:message>
		</c:when>
		<c:when test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION.cause, 'InvalidPassword')}">
			<fmt:message key="vulpe.security.error.invalid.password" />
		</c:when>
		<c:when test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION.cause, 'InactiveUser')}">
			<fmt:message key="vulpe.security.error.invalid.user">
				<fmt:param value="${vulpeUserAuthentication}" />
			</fmt:message>
		</c:when>
	</c:choose>
	</p>
	<script type="text/javascript">
	$(document).ready(function() {
		vulpe.view.hideLoading();
	});
	</script>
	</c:if>
	<v:text elementId="j_username" name="j_username" targetName="" maxlength="20" labelKey="label.vulpe.security.login.username" lowerCase="true" value="${not empty param.loginError ? vulpeUserAuthentication : ''}" style="width: 150px;" />
	<v:password elementId="j_password" name="j_password" targetName="" maxlength="20" labelKey="label.vulpe.security.login.password" style="width: 150px;" />
	<p>
		<input name="submit" type="submit" id="buttonSubmitLoginForm" value="<fmt:message key='label.vulpe.security.login'/>" class="vulpeButton" style="width: 75px;"/>&nbsp;<input name="reset"type="reset" value="<fmt:message key='label.vulpe.security.login.clear'/>" class="vulpeButton" style="width: 75px;" />
	</p>
<script type="text/javascript">
$(document).ready(function() {
	vulpe.config.authenticator.url.redirect = "${SPRING_SECURITY_SAVED_REQUEST_KEY.redirectUrl}";
	if (vulpe.config.authenticator.url.redirect == "" || vulpe.config.authenticator.url.redirect.indexOf("/autenticator") != -1) {
		vulpe.config.authenticator.url.redirect = "${pageContext.request.contextPath}/index.jsp";
	}
	var j_username = vulpe.util.getElement('j_username');
	var j_password = vulpe.util.getElement('j_password');
	if (j_username.value == '') {
		j_username.focus();
	} else if (j_password.value == '') {
		j_password.focus();
	}
	vulpe.util.get('buttonSubmitLoginForm').bind('click', function() {
		vulpe.view.request.submitLoginForm({
			formName: 'vulpeLoginForm',
			layerFields: 'vulpeLoginForm',
			layer: 'body',
			validate: false,
			beforeJs: "vulpe.login.executeBefore()",
			afterJs: function() {vulpe.login.executeAfter()}
		});
		return false;
	});
});
</script>
</div>
</form>