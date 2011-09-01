<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:select labelKey="label.mmn.ministry.MemberReport.select.month" property="month"
			showBlank="true" autoLoad="false" /></td>
		<td><v:select labelKey="label.mmn.ministry.MemberReport.select.ministryType"
			property="ministryType" autoLoad="false" showBlank="true" /></td>
		<td><v:selectPopup labelKey="label.mmn.ministry.MemberReport.select.member" property="member"
			identifier="id" description="name" action="/core/Member/select" popupId="memberSelectPopup"
			popupProperties="member.id=id,member.name=name" size="40" popupWidth="420px" autocomplete="true"
			autocompleteMinLength="1" autocompleteValueList="${ever['membersAutocompleteValueList']}" /></td>
	</tr>
</table>