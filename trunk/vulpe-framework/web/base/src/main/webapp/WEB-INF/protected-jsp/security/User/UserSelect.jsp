<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:text labelKey="label.vulpe.security.User.select.name" property="name" size="40" />
<v:text labelKey="label.vulpe.security.User.select.username" property="username" size="20" lowerCase="true" />
<v:checkbox labelKey="label.vulpe.security.User.select.active" property="active"
	fieldValue="${true}" />
