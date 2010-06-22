<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="8"><fmt:message key="label.gmn.anuncios.Leitor.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.gmn.anuncios.Leitor.select.publicador"
				property="publicador.nome"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.anuncios.Leitor.select.data"
				property="data"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.anuncios.Leitor.select.tipoLeitura"
				property="tipoLeitura"
				sort="true"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="8"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
