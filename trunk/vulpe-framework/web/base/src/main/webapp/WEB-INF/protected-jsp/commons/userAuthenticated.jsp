<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<div id="userAuthenticated">
	<fmt:message key="label.vulpe.security.authenticated.welcome">
		<fmt:param value="${ever['VulpeSecurityContext'].user.name}" />
	</fmt:message>
	<a href="javascript:void(0);" class="vulpeChangePassword">
		<fmt:message key="label.vulpe.security.change.password" />
	</a>
	<a href="javascript:void(0)" class="vulpeLogout">
		<fmt:message key="label.vulpe.security.logoff" />
	</a>
</div>