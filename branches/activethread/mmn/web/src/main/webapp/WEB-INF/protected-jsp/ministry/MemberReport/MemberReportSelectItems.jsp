<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="12"><fmt:message key="label.mmn.ministry.MemberReport.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.month"
				property="month"
				sort="true" width="10%"
			/>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.ministryType"
				property="ministryType"
				sort="true" width="15%"
			/>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.member"
				property="member.name"
				sort="true" width="30%"
			/>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.books"
				property="books"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.brochures"
				property="brochures"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.hours"
				property="hours"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.magazines"
				property="magazines"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.revisits"
				property="revisits"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.studies"
				property="studies"
				sort="true"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="12"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
