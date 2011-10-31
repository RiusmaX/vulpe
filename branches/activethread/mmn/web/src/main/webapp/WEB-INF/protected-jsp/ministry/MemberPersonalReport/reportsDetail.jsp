<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="3"><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.total"/></th>
		<th>${now.totalBooks}</th>
		<th>${now.totalBrochures}</th>
		<th>${now.totalHours}${now.totalPioneer}</th>
		<th>${now.totalMagazines}</th>
		<th>${now.totalRevisits}</th>
		<th></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.mmn.ministry.MemberPersonalReport.main.reports.date">
				<v:date property="date" />
			</v:column>
			<v:column labelKey="label.mmn.ministry.MemberPersonalReport.main.reports.books">
				<v:text property="books" mask="I" size="8" />
			</v:column>
			<v:column labelKey="label.mmn.ministry.MemberPersonalReport.main.reports.brochures">
				<v:text property="brochures" mask="I" size="8" />
			</v:column>
			<v:column labelKey="label.mmn.ministry.MemberPersonalReport.main.reports.hours">
				<v:text property="hours" mask="99:99" />
			</v:column>
			<v:column labelKey="label.mmn.ministry.MemberPersonalReport.main.reports.magazines">
				<v:text property="magazines" mask="I" size="8" />
			</v:column>
			<v:column labelKey="label.mmn.ministry.MemberPersonalReport.main.reports.revisits">
				<v:text property="revisits" mask="I" size="8" />
			</v:column>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="3"><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.total"/></th>
		<th>${now.totalBooks}</th>
		<th>${now.totalBrochures}</th>
		<th>${now.totalHours}${now.totalPioneer}</th>
		<th>${now.totalMagazines}</th>
		<th>${now.totalRevisits}</th>
		<th></th>
	</jsp:attribute>
</v:table>
