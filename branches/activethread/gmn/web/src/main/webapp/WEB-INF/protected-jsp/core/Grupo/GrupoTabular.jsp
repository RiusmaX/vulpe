<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="6"><fmt:message key="label.gmn.core.Grupo.tabular.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.gmn.core.Grupo.tabular.nome">
				<v:text
					property="nome"
					size="40"
					maxlength="40"
					validateType="STRING"
					validateMinLength="5"
					validateMaxLength="40"
				/>
			</v:column>
			<v:column labelKey="label.gmn.core.Grupo.tabular.congregacao">
				<v:select
					property="congregacao.id"
					items="Congregacao"
					itemKey="id"
					itemLabel="nome"
					showBlank="true" autoLoad="true"
					required="true"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="6"><fmt:message key="vulpe.total.records"/>&nbsp;<v:tabular showSize="true"/></th>
	</jsp:attribute>
</v:table>
