<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="6"><fmt:message key="label.portal.core.Section.tabular.header" /></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.portal.core.Section.tabular.name">
				<v:textTranslate property="name" size="35" />
			</v:column>
			<v:column labelKey="label.portal.core.Section.tabular.description">
				<v:textTranslate property="description" size="55" />
			</v:column>
			<v:column labelKey="label.portal.core.Section.tabular.status">
				<v:select property="status" />
			</v:column>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="6"><fmt:message key="vulpe.total.records" />&nbsp;<v:paging showSize="true" /></th>
	</jsp:attribute>
</v:table>
