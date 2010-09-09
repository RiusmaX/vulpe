<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:selectPopup labelKey="label.gmn.publicacoes.Pedido.crud.publicador"
			property="publicador" identifier="id" description="nome" action="/core/Publicador/select"
			popupId="publicadorSelectPopup" popupProperties="publicador.id=id,publicador.nome=nome" size="40"
			popupWidth="420px" required="true" autocomplete="true" autocompleteMinLength="1" /></td>
		<td><v:date labelKey="label.gmn.publicacoes.Pedido.crud.data" property="data" required="true" />
		</td>
		<td><v:checkbox labelKey="label.gmn.publicacoes.Pedido.crud.entregue"
			property="entregue" fieldValue="true" /></td>
	</tr>
</table>