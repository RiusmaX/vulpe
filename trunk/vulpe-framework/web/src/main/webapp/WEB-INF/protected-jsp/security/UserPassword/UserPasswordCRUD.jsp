<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:show labelKey="label.vulpe.security.User.crud.username" property="username" paragraph="true" />
<v:password labelKey="label.vulpe.security.User.crud.currentPassword" property="currentPassword"
	maxlength="20" required="true" />
<v:password labelKey="label.vulpe.security.User.crud.newPassword" property="password" maxlength="20"
	required="true" />
<v:password labelKey="label.vulpe.security.User.crud.newPasswordConfirm" property="passwordConfirm"
	maxlength="20" required="true" />
