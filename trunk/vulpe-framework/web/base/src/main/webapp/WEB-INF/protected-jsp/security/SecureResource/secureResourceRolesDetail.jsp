<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<fieldset>
<legend><fmt:message key="label.vulpe.security.SecureResource.main.secureResourceRoles.select.roles"/></legend>
<v:checkboxlist list="now['cachedClasses']['Role']" listKey="id"
	listValue="description" property="secureResourceRoles" detail="true" targetName="entity"
	detailProperty="role" labelStyle="display: inline"/>
</fieldset>