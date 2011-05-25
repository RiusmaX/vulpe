<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id" />
<v:hidden property="member.id" />
<v:hidden property="date" />
<table>
	<tr>
		<td><v:select labelKey="label.mmn.ministry.MemberPersonalReport.main.month" property="month"
			showBlank="true" autoLoad="false" /></td>
		<td><v:select labelKey="label.mmn.ministry.MemberPersonalReport.main.ministryType"
			property="ministryType" showBlank="true" autoLoad="false" /></td>
		<td><v:text labelKey="label.mmn.ministry.MemberPersonalReport.main.studies"
			property="studies" mask="I" size="8" /></td>
	</tr>
</table>