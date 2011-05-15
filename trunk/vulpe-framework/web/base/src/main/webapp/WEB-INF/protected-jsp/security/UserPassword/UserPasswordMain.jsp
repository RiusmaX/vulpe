<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:text labelKey="label.vulpe.security.User.main.username" property="username"
	showAsText="true" />
<v:password labelKey="label.vulpe.security.User.main.currentPassword" property="currentPassword"
	maxlength="20" required="true" />
<v:password labelKey="label.vulpe.security.User.main.newPassword" property="password" maxlength="20"
	required="true" />
<v:password labelKey="label.vulpe.security.User.main.newPasswordConfirm" property="passwordConfirm"
	maxlength="20" required="true" />
