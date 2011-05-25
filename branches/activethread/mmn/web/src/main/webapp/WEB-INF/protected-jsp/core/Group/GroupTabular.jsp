<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="6"><fmt:message key="label.mmn.core.Group.tabular.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.mmn.core.Group.tabular.name">
				<v:text
					property="name"
					size="40"
					maxlength="40"
					validateType="STRING"
					validateMinLength="5"
					validateMaxLength="40"
				/>
			</v:column>
			<v:column labelKey="label.mmn.core.Group.tabular.congregation">
				<v:select
					property="congregation.id"
					items="Congregation"
					itemKey="id"
					itemLabel="name"
					showBlank="true" autoLoad="true"
					required="true"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="6"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
