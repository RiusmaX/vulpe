<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="9"><fmt:message key="label.sirci.core.Apontamento.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.sirci.core.Apontamento.select.descricao"
				property="descricao"
				sort="true"
			/>
			<v:column
				labelKey="label.sirci.core.Apontamento.select.tipoApontamento"
				property="tipoApontamento.descricao"
				sort="true"
			/>
			<v:column
				labelKey="label.sirci.core.Apontamento.select.situacaoApontamento"
				property="situacaoApontamento"
				sort="true"
			/>
			<v:column
				labelKey="label.sirci.core.Apontamento.select.impactoApontamento"
				property="impactoApontamento"
				sort="true"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="9"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
