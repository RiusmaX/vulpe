<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>

<v:hidden property="id" />
<v:text labelKey="label.vulpe.security.User.crud.username" property="username" required="true" lowerCase="true" validateType="STRING" validateMinLength="3" />
<v:password labelKey="label.vulpe.security.User.crud.password" property="password" requiredField="passwordConfirm" maxlength="20"  />
<v:password labelKey="label.vulpe.security.User.crud.passwordConfirm" property="passwordConfirm" maxlength="20"  />
<v:text labelKey="label.vulpe.security.User.crud.name" property="name" required="true" validateType="STRING" validateMinLength="3" size="40" maxlength="100" />
<v:text labelKey="label.vulpe.security.User.crud.email" property="email" required="true" lowerCase="true" validateType="EMAIL" size="60" maxlength="150" />
<v:checkbox labelKey="label.vulpe.security.User.crud.active" property="active" fieldValue="${true}" />