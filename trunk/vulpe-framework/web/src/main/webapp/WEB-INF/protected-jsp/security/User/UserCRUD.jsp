<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>

<v:hidden property="id"/>
<v:text labelKey="label.vulpe.security.User.crud.username" property="username"/>
<v:password labelKey="label.vulpe.security.User.crud.password" property="password"/>
<v:password labelKey="label.vulpe.security.User.crud.passwordConfirm" property="passwordConfirm"/>
<v:text labelKey="label.vulpe.security.User.crud.name" property="name"/>
<v:text labelKey="label.vulpe.security.User.crud.email" property="email"/>
<v:checkbox labelKey="label.vulpe.security.User.crud.active" property="active" fieldValue="${true}"/>