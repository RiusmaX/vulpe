<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<div class="line">
<v:text labelKey="label.mmn.core.Member.select.name" property="name" size="30"
			maxlength="60" />
<v:select labelKey="label.mmn.core.Member.select.gender" property="gender"
			showBlank="true" autoLoad="false" />
<v:select labelKey="label.mmn.core.Member.select.group" property="group.id"
			items="${ever['groupsOfSelectedCongregation']}" itemKey="id" itemLabel="name" showBlank="true"
			autoLoad="false" />
<v:select labelKey="label.mmn.core.Member.select.ministryType"
			property="ministryType" showBlank="true" autoLoad="false" />
</div><br/>
<div class="line">
<v:select labelKey="label.mmn.core.Member.select.responsibility"
			property="responsibility" showBlank="true" autoLoad="false" />
<v:checkboxlist labelKey="label.mmn.core.Member.select.additionalPrivileges"
			property="additionalPrivileges" enumeration="AdditionalPrivilege" />
</div>