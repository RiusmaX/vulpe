<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:menu labelKey="label.vulpe.menu.Security">
	<v:menu elementId="Role" labelKey="label.vulpe.menu.Security.Role" action="/security/Role/tabular"/>
	<v:menu elementId="User" labelKey="label.vulpe.menu.Security.User" action="/security/User/select"/>
	<v:menu elementId="SecureResource" labelKey="label.vulpe.menu.Security.Resource" action="/security/SecureResource/select" />
</v:menu>