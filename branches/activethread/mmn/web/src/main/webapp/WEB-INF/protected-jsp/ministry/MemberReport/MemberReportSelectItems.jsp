<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="8"><fmt:message key="label.mmn.ministry.MemberReport.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.month"
				property="month"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.ministryType"
				property="ministryType"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.member"
				property="member.name"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.ministry.MemberReport.select.hours"
				property="hours"
				sort="true"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="8"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
