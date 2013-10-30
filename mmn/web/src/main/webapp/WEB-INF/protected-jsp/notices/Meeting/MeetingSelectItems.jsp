<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="7"><fmt:message key="label.mmn.notices.Meeting.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.mmn.notices.Meeting.select.president"
				property="president.name"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.notices.Meeting.select.date"
				property="date"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="7"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
