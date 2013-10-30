<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="10"><fmt:message key="label.mmn.publications.Order.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.mmn.publications.Order.select.member"
				property="member.name"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.publications.Order.select.date"
				property="date"
			/>
			<v:column
				labelKey="label.mmn.publications.Order.select.validityDate"
				property="validityDate"
			/>
			<v:column
				labelKey="label.mmn.publications.Order.select.delivered"
				property="delivered"
				booleanTo="{Yes}|{No}"
			/>
			<v:column
				labelKey="label.mmn.publications.Order.select.deliveryDate"
				property="deliveryDate"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="10"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
