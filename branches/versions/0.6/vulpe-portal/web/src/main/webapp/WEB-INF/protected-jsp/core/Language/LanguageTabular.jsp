<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="7"><fmt:message key="label.portal.core.Language.tabular.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.portal.core.Language.tabular.localeCode">
				<v:text
					property="localeCode"
					size="10"
				/>
			</v:column>
			<v:column labelKey="label.portal.core.Language.tabular.name">
				<v:text
					property="name"
					size="30"
				/>
			</v:column>
			<v:column labelKey="label.portal.core.Language.tabular.defaultLanguage">
				<v:checkbox
					property="defaultLanguage"
					fieldValue="true" onclick="vulpe.view.markOnlyOne(this, 'defaultLanguage');"
				/>
			</v:column>
			<v:column labelKey="label.portal.core.Language.tabular.status">
				<v:select
					property="status"
					autoLoad="false"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="7"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
