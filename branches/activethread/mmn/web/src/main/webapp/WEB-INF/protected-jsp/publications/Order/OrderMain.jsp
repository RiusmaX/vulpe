<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:selectPopup labelKey="label.mmn.publications.Order.main.member" property="member"
			identifier="id" description="name" action="/core/Member/select" popupId="memberSelectPopup"
			popupProperties="member.id=id,member.name=name" size="40" popupWidth="420px" required="true"
			autocomplete="true" autocompleteMinLength="1" autocompleteValueList="${membersAutocompleteValueList}" /></td>
		<td><v:date labelKey="label.mmn.publications.Order.main.date" property="date" required="true" />
		</td>
		<td><v:checkbox labelKey="label.mmn.publications.Order.main.delivered" property="delivered"
			fieldValue="true" /></td>
	</tr>
</table>