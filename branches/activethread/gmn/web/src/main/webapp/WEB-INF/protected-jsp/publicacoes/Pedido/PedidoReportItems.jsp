<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="10"><fmt:message key="label.gmn.publicacoes.Pedido.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.gmn.publicacoes.Pedido.select.publicador"
				property="publicador.nome"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.publicacoes.Pedido.select.data"
				property="data"
			/>
			<v:column
				labelKey="label.gmn.publicacoes.Pedido.select.dataValidade"
				property="dataValidade"
			/>
			<v:column
				labelKey="label.gmn.publicacoes.Pedido.select.entregue"
				property="entregue"
				booleanTo="{vulpe.label.true.yes}|{vulpe.label.false.no}"
			/>
			<v:column
				labelKey="label.gmn.publicacoes.Pedido.select.dataEntrega"
				property="dataEntrega"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="10"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
