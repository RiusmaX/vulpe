<%@include file="/WEB-INF/protected-jsp/common/common.jsp"%>

<v:text labelKey="label.vulpe.security.User.select.name" property="name" size="40" required="true" />
<v:text labelKey="label.vulpe.security.User.select.username" property="username" size="40" />
<v:checkbox labelKey="label.vulpe.security.User.select.active" property="active"
	fieldValue="${true}" />
