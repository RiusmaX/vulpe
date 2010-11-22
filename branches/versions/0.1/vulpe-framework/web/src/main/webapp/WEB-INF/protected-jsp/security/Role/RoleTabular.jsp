<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<table>
	<tr>
		<td><v:text labelKey="label.vulpe.security.Role.select.description"
			targetName="entitySelect" property="description" size="60" maxlength="60" /></td>
	</tr>
</table>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="6"><fmt:message key="label.vulpe.security.Role.select.header" /></th>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="6"><fmt:message key="vulpe.total.records" /> <v:paging showSize="true"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.vulpe.security.Role.select.name">
				<v:text property="simpleName" size="40" upperCase="true" validateType="STRING"
					validateMinLength="5" validateMaxLength="40" requiredField="description" />
			</v:column>
			<v:column labelKey="label.vulpe.security.Role.select.description">
				<v:text property="description" size="60" validateType="STRING" validateMinLength="5"
					validateMaxLength="60" />
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>