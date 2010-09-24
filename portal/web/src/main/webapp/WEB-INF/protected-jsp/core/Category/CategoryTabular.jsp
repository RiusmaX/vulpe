<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="5"><fmt:message key="label.portal.core.Category.tabular.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.portal.core.Category.tabular.name">
				<v:textTranslate
					property="name"
					size="40"
				/>
			</v:column>
			<v:column labelKey="label.portal.core.Category.tabular.description">
				<v:textTranslate
					property="description"
					size="60"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="5"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
