<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column>
			<table>
				<tr>
					<td><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.date" /></td>
					<td><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.books" /></td>
					<td><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.brochures" /></td>

				</tr>
				<tr>
					<td><v:date property="date" /></td>
					<td><v:text property="books" mask="I" size="8" /></td>
					<td><v:text property="brochures" mask="I" size="8" /></td>
				</tr>
				<tr>
					<td><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.hours" /></td>
					<td><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.magazines" /></td>
					<td><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.revisits" /></td>
				</tr>
				<tr>
					<td><v:text property="hours" mask="I" size="8" /></td>
					<td><v:text property="magazines" mask="I" size="8" /></td>
					<td><v:text property="revisits" mask="I" size="8" /></td>
				</tr>
			</table>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>
