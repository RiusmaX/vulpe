<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:show labelKey="label.vulpe.security.User.main.username" property="username" paragraph="true" />
<v:password labelKey="label.vulpe.security.User.main.currentPassword" property="currentPassword"
	maxlength="20" required="true" />
<v:password labelKey="label.vulpe.security.User.main.newPassword" property="password" maxlength="20"
	required="true" />
<v:password labelKey="label.vulpe.security.User.main.newPasswordConfirm" property="passwordConfirm"
	maxlength="20" required="true" />
