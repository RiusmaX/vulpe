<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>

<fieldset>
<legend><fmt:message key="label.vulpe.security.User.main.userRoles.select.roles"/></legend>
<v:checkboxlist list="now['cachedClasses']['Role']" listKey="id"
	listValue="description" property="userRoles" detail="true" targetName="entity"
	detailProperty="role" labelStyle="display: inline"/>
</fieldset>