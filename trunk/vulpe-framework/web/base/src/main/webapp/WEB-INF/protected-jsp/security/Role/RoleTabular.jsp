<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="6"><fmt:message key="label.vulpe.security.Role.select.header" /></th>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="6"><fmt:message key="vulpe.total.records" />&nbsp;<v:paging showSize="true" /></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.vulpe.security.Role.select.name">
				<v:text property="simpleName" size="40" upperCase="true" validateType="STRING" validateMinLength="5"
					requiredFields="description" />
			</v:column>
			<v:column labelKey="label.vulpe.security.Role.select.description">
				<v:text property="description" size="60" validateType="STRING" validateMinLength="5" />
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>