<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="9"><fmt:message key="label.portal.core.Content.select.header" /></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.portal.core.Content.select.section" property="section.name" />
			<v:column labelKey="label.portal.core.Content.select.category" property="category.name" />
			<v:column labelKey="label.portal.core.Content.select.description" property="description" />
			<v:column labelKey="label.portal.core.Content.select.viewsClicks" property="viewsClicks" />
			<v:column labelKey="label.portal.core.Content.select.status" property="status" />
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="9"><fmt:message key="vulpe.total.records" />&nbsp;<v:paging showSize="true" /></th>
	</jsp:attribute>
</v:table>
