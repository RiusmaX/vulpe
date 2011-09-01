<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:text labelKey="label.mmn.core.Member.select.name" property="name" size="30"
			maxlength="60" /></td>
		<td><v:select labelKey="label.mmn.core.Member.select.gender" property="gender"
			showBlank="true" autoLoad="false" /></td>
		<td><v:select labelKey="label.mmn.core.Member.select.group" property="group.id"
			items="${ever['groupsOfSelectedCongregation']}" itemKey="id" itemLabel="name" showBlank="true"
			autoLoad="false" /></td>
		<td><v:select labelKey="label.mmn.core.Member.select.ministryType"
			property="ministryType" showBlank="true" autoLoad="false" /></td>
	</tr>
	<tr>
		<td><v:select labelKey="label.mmn.core.Member.select.responsibility"
			property="responsibility" showBlank="true" autoLoad="false" /></td>
		<td colspan="3"><v:checkboxlist labelKey="label.mmn.core.Member.select.additionalPrivileges"
			property="additionalPrivileges" enumeration="AdditionalPrivilege" /></td>
	</tr>
</table>