<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<v:text labelKey="label.vulpe.security.User.main.username" property="username" required="true" lowerCase="true" validateType="STRING" validateMinLength="3" showAsText="${not empty entity.id}" />
<v:password labelKey="label.vulpe.security.User.main.password" property="password" requiredFields="passwordConfirm" maxlength="20" required="${empty entity.id}" />
<v:password labelKey="label.vulpe.security.User.main.passwordConfirm" property="passwordConfirm" maxlength="20"  />
<v:text labelKey="label.vulpe.security.User.main.name" property="name" required="true" validateType="STRING" validateMinLength="3" size="40" maxlength="100" />
<v:text labelKey="label.vulpe.security.User.main.email" property="email" required="true" lowerCase="true" validateType="EMAIL" size="60" maxlength="150" />
<v:checkbox labelKey="label.vulpe.security.User.main.active" property="active" fieldValue="${true}" />