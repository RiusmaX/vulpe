<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td width="33%"><v:text labelKey="label.mmn.core.Member.main.name" property="name" size="40"
			maxlength="60" required="true" /></td>
		<td width="33%"><v:select labelKey="label.mmn.core.Member.main.gender" property="gender"
			showBlank="false" autoLoad="false" onchange="app.core.privileges('baptized')" /></td>
		<td width="33%"><v:select labelKey="label.mmn.core.Member.main.group" property="group.id"
			items="${ever['groupsOfSelectedCongregation']}" itemKey="id" itemLabel="name" showBlank="false"
			autoLoad="false" /></td>
	</tr>
	<tr>
		<td><v:checkbox labelKey="label.mmn.core.Member.main.baptized" property="baptized"
			fieldValue="true" onclick="app.core.privileges(this.id)" /></td>
		<td><span id="ministryType" style="${entity.baptized ? '' : 'display: none'}"><v:select
			labelKey="label.mmn.core.Member.main.ministryType" property="ministryType" showBlank="false"
			autoLoad="false" items="${ministryTypeList}" /></span> <span id="simpleMinistryType"
			style="${entity.baptized ? 'display: none' : ''}"><v:select
			labelKey="label.mmn.core.Member.main.ministryType" property="simpleMinistryType"
			showBlank="false" autoLoad="false" items="${simpleMinistryTypeList}" /></span></td>
		<td><span id="privilege"
			style="${entity.baptized && entity.gender == 'MALE' ? '' : 'display: none'}"><v:select
			labelKey="label.mmn.core.Member.main.responsibility" property="responsibility" showBlank="true"
			autoLoad="false" onchange="app.core.additionalPrivileges(this)" /></span></td>
	</tr>
	<tr>
		<td colspan="3">
		<fieldset id="additionalPrivilege"
			style="${entity.baptized && entity.gender == 'MALE' ? '' : 'display: none'}"><legend><v:label
			key="label.mmn.core.Member.main.additionalPrivileges" /></legend> <v:checkboxlist
			property="additionalPrivileges" enumeration="AdditionalPrivilege" /></fieldset>
		</td>
	</tr>
	<tr>
		<td colspan="3"><v:selectPopup labelKey="label.mmn.core.Member.main.user" property="user"
			identifier="id" description="name" action="/security/User/select" popupId="userSelectPopup"
			popupProperties="user.id=id,user.name=name" size="40" popupWidth="420px" autocomplete="true" />
		</td>
	</tr>
</table>
<c:if test="${empty entity.responsibility}">
<script type="text/javascript">
jQuery(function() {
	app.core.showHideAdditionalPrivileges(4, false);
});
</script>
</c:if>