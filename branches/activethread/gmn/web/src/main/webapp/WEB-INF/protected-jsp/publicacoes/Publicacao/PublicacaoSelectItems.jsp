<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="9"><fmt:message key="label.gmn.publicacoes.Publicacao.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row popupProperties="id,codigo,nome">
			<v:column
				labelKey="label.gmn.publicacoes.Publicacao.select.codigo"
				property="codigo"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.publicacoes.Publicacao.select.nome"
				property="nome"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.publicacoes.Publicacao.select.tipoPublicacao"
				property="tipoPublicacao.descricao"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.publicacoes.Publicacao.select.classificacaoPublicacao"
				property="classificacaoPublicacao"
				sort="true"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="9"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
