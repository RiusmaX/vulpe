<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<form action="${pageContext.request.contextPath}/j_spring_security_check" method="POST"
	id="vulpeLoginForm">
<div id="contentTitle"><fmt:message>vulpe.security.login.title.application</fmt:message></div>
<div id="content"><c:if
	test="${not empty param.loginError}">
	<p><font color="red"><fmt:message key="vulpe.security.message.error.login.failed" /> <c:choose>
		<c:when test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION.cause, 'UserNotFound')}">
			<fmt:message key="vulpe.security.message.error.user.not.found">
				<fmt:param value="${SPRING_SECURITY_LAST_USERNAME}" />
			</fmt:message>
		</c:when>
		<c:when test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION.cause, 'InvalidPassword')}">
			<fmt:message key="vulpe.security.message.error.invalid.password" />
		</c:when>
		<c:when test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION.cause, 'InactiveUser')}">
			<fmt:message key="vulpe.security.message.error.invulpe.user">
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
		var URLRequested = "${vulpeSecurityURLRequested}";
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
		
		if (URLRequested && URLRequested.indexOf('/ajax.action') != -1) {
			vulpe.util.get('buttonSubmitLoginForm').click(function() {
				vulpe.view.request.submitLoginForm('vulpeLoginForm', 'vulpeLoginForm', '', 'body', false, '', ''); 
				return false;
			});
		} else {
			vulpe.util.get('buttonSubmitLoginForm').click(function() {
				return vulpe.validate.validateLoginForm('vulpeLoginForm'); 
			});
		}
	});
</script>
</div>