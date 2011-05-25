<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<v:hidden property="date" />
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:select labelKey="label.mmn.ministry.MemberReport.main.month" property="month"
			showBlank="true" autoLoad="false" required="true" /></td>
		<td><v:select labelKey="label.mmn.ministry.MemberReport.main.ministryType"
			property="ministryType" autoLoad="false" required="true" /></td>
		<td><v:selectPopup labelKey="label.mmn.ministry.MemberReport.main.member"
			autocompleteMinLength="1" property="member" identifier="id" description="name"
			action="/core/Member/select" popupId="memberSelectPopup"
			popupProperties="member.id=id,member.name=name" size="35" popupWidth="420px" autocomplete="true"
			autocompleteValueList="${membersAutocompleteValueList}" required="true" autocompleteProperties="ministryType" /></td>
	</tr>
	<tr>
		<td colspan="3">
		<table width="100%" cellpadding="0" cellspacing="0" style="margin-left: 0px;">
			<tr>
				<td><v:text labelKey="label.mmn.ministry.MemberReport.main.books" property="books" mask="I"
					size="8" maxlength="8" /></td>
				<td><v:text labelKey="label.mmn.ministry.MemberReport.main.brochures" property="brochures"
					mask="I" size="8" maxlength="8" /></td>
				<td><v:text labelKey="label.mmn.ministry.MemberReport.main.hours" property="hours" mask="I"
					size="8" maxlength="10" style="background-color: #FFFFDD" /></td>
				<td><v:text labelKey="label.mmn.ministry.MemberReport.main.magazines" property="magazines"
					mask="I" size="8" maxlength="8" /></td>
				<td><v:text labelKey="label.mmn.ministry.MemberReport.main.revisits" property="revisits"
					mask="I" size="8" maxlength="8" /></td>
				<td><v:text labelKey="label.mmn.ministry.MemberReport.main.studies" property="studies"
					mask="I" size="8" maxlength="8" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>