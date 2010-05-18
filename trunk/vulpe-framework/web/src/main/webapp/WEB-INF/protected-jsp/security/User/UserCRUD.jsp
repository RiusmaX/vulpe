<%@include file="/WEB-INF/protected-jsp/common/common.jsp"%>

<v:hidden property="id" />
<v:text labelKey="label.vulpe.security.User.crud.username" property="username" required="true" />
<v:password labelKey="label.vulpe.security.User.crud.password" property="password" required="true" />
<v:password labelKey="label.vulpe.security.User.crud.passwordConfirm" property="passwordConfirm"
	required="true" />
<v:text labelKey="label.vulpe.security.User.crud.name" property="name" required="true" />
<v:text labelKey="label.vulpe.security.User.crud.email" property="email" required="true" />
<v:checkbox labelKey="label.vulpe.security.User.crud.active" property="active" fieldValue="${true}" />