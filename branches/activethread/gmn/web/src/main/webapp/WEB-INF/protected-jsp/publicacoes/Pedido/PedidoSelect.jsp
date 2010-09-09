<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:selectPopup labelKey="label.gmn.publicacoes.Pedido.select.publicador"
			property="publicador" identifier="id" description="nome" action="/core/Publicador/select"
			popupId="publicadorSelectPopup" popupProperties="publicador.id=id,publicador.nome=nome" size="40"
			popupWidth="420px" autocomplete="true" autocompleteMinLength="1" /></td>
		<td><v:checkbox labelKey="label.gmn.publicacoes.Pedido.select.entregue" property="entregue"
			fieldValue="true" /></td>
	</tr>
	<tr>
		<td colspan="5">
<p><v:label key="label.gmn.publicacoes.Pedido.select.periodo" /><br>
<v:date property="dataInicial" paragraph="false" />até<v:date property="dataFinal"
	paragraph="false" /></p></td>
	</tr>
</table>