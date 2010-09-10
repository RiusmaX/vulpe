<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:select labelKey="label.gmn.ministerio.Relatorio.select.mes" property="mes"
			showBlank="true" autoLoad="false" /></td>
		<td><v:select labelKey="label.gmn.ministerio.Relatorio.select.tipoMinisterio"
			property="tipoMinisterio" autoLoad="false" showBlank="true" /></td>
		<td><v:selectPopup labelKey="label.gmn.ministerio.Relatorio.select.publicador"
			property="publicador" identifier="id" description="nome" action="/core/Publicador/select"
			popupId="publicadorSelectPopup" popupProperties="publicador.id=id,publicador.nome=nome" size="40"
			popupWidth="420px" autocomplete="true" autocompleteMinLength="1" /></td>
	</tr>
</table>