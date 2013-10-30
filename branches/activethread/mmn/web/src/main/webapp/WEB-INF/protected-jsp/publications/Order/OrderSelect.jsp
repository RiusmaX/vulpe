<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:selectPopup labelKey="label.mmn.publications.Order.select.member" property="member"
			identifier="id" description="name" action="/core/Member/select" popupId="memberSelectPopup"
			popupProperties="member.id=id,member.name=name" size="40" popupWidth="420px" autocomplete="true"
			autocompleteMinLength="1" autocompleteValueList="${ever['membersAutocompleteValueList']}" /></td>
		<td><v:checkbox labelKey="label.mmn.publications.Order.select.delivered" property="delivered"
			fieldValue="true" /></td>
	</tr>
	<tr>
		<td colspan="5">
		<p><v:label key="label.mmn.publications.Order.select.period" /><br>
		<v:date property="initialDate" paragraph="false" />at�<v:date property="finalDate"
			paragraph="false" /></p>
		</td>
	</tr>
</table>