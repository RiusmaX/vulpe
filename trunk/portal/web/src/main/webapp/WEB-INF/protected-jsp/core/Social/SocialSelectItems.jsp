<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="9"><fmt:message key="label.portal.core.Social.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.portal.core.Social.select.network"
				property="network"
			/>
			<v:column
				labelKey="label.portal.core.Social.select.name"
				property="name"
			/>
			<v:column
				labelKey="label.portal.core.Social.select.target"
				property="target"
			/>
			<v:column
				labelKey="label.portal.core.Social.select.status"
				property="status"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="9"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
