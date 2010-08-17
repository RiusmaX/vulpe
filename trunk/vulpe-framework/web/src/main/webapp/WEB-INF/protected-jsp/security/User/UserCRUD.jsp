<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>

<v:hidden property="id" />
<c:choose>
<c:when test="${not empty entity.id}">
<v:show property="username" labelKey="label.vulpe.security.User.crud.username" paragraph="true" />
</c:when>
<c:otherwise>
<v:text labelKey="label.vulpe.security.User.crud.username" property="username" required="true" lowerCase="true" validateType="STRING" validateMinLength="3" />
</c:otherwise>
</c:choose>
<v:password labelKey="label.vulpe.security.User.crud.password" property="password" requiredField="passwordConfirm" maxlength="20"  />
<v:password labelKey="label.vulpe.security.User.crud.passwordConfirm" property="passwordConfirm" maxlength="20"  />
<v:text labelKey="label.vulpe.security.User.crud.name" property="name" required="true" validateType="STRING" validateMinLength="3" size="40" maxlength="100" />
<v:text labelKey="label.vulpe.security.User.crud.email" property="email" required="true" lowerCase="true" validateType="EMAIL" size="60" maxlength="150" />
<v:checkbox labelKey="label.vulpe.security.User.crud.active" property="active" fieldValue="${true}" />