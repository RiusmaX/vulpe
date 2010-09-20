<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<div id="userAuthenticated">
	<fmt:message key="label.vulpe.security.logged.welcome">
		<fmt:param value="${securityContext.user.name}" />
	</fmt:message>
	<a href="javascript:void(0);" onclick="vulpe.view.request.submitLink('/security/UserPassword/update/ajax');">
		<fmt:message key="label.vulpe.security.change.password" />
	</a>
	<a href="${pageContext.request.contextPath}/j_spring_security_logout">
		<fmt:message key="label.vulpe.security.logoff" />
	</a>
</div>