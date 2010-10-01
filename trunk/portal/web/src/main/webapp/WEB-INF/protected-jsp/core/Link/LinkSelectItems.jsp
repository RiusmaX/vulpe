<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="10"><fmt:message key="label.portal.core.Link.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.portal.core.Link.select.section"
				property="section.name"
			/>
			<v:column
				labelKey="label.portal.core.Link.select.category"
				property="category.name"
			/>
			<v:column
				labelKey="label.portal.core.Link.select.name"
				property="name"
			/>
			<v:column
				labelKey="label.portal.core.Link.select.url"
				property="url"
			/>
			<v:column
				labelKey="label.portal.core.Link.select.positions"
				property="positions" enumType="Position"
			/>
			<v:column
				labelKey="label.portal.core.Link.select.clicks"
				property="clicks"
			/>
			<v:column
				labelKey="label.portal.core.Link.select.status"
				property="status"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="10"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
