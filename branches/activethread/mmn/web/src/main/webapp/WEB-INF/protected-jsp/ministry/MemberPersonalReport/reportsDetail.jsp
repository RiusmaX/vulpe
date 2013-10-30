<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:if test="${entity.ministryType == 'AUXILIARY_PIONEER' || entity.ministryType == 'REGULAR_PIONEER'}">
<table cellpadding="0" cellspacing="0" class="vulpeEntities">
	<tr class="vulpeTableHeader">
		<td width="25%"><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.totalAveragePerDay" /></td>
		<td width="25%"><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.totalPioneerRemain" /></td>
		<td width="25%"><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.totalLeftDays" /></td>
		<td width="25%"><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.totalPioneerRemainPerDay" /></td>
	</tr>
	<tr class="vulpeLineOn" style="font-weight: bold">
		<td>${entity.totalAveragePerDay}</td>
		<td>${entity.totalPioneerRemain}</td>
		<td>${entity.totalLeftDays}</td>
		<td>${entity.totalPioneerRemainPerDay}</td>
	</tr>
</table>
</c:if>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="3"><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.total" /></th>
		<th>${entity.totalBooks}</th>
		<th>${entity.totalBrochures}</th>
		<th>${entity.totalHours}${entity.totalPioneer}</th>
		<th>${entity.totalMagazines}</th>
		<th>${entity.totalRevisits}</th>
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
		<th colspan="3"><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.total" /></th>
		<th><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.books" /><br />${entity.totalBooks}</th>
		<th><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.brochures" /><br />${entity.totalBrochures}</th>
		<th><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.hours" /><br />${entity.totalHours}${entity.totalPioneer}</th>
		<th><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.magazines" /><br />${entity.totalMagazines}</th>
		<th><v:label key="label.mmn.ministry.MemberPersonalReport.main.reports.revisits" /><br />${entity.totalRevisits}</th>
		<th></th>
	</jsp:attribute>
</v:table>
