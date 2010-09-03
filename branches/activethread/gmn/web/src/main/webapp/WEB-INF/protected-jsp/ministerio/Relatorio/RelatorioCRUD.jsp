<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:date labelKey="label.gmn.ministerio.Relatorio.crud.data" property="data" /></td>
		<td colspan="2"><v:selectPopup labelKey="label.gmn.ministerio.Relatorio.crud.publicador" autoCompleteMinLength="1"
			property="publicador" identifier="id" description="nome" action="/core/Publicador/select"
			popupId="publicadorSelectPopup" popupProperties="publicador.id=id,publicador.nome=nome" size="40"
			popupWidth="420px" autoComplete="true" required="true" /></td>
	</tr>
	<tr>
		<td><v:text labelKey="label.gmn.ministerio.Relatorio.crud.livros" property="livros" mask="I"
			size="10" maxlength="10" /></td>
		<td><v:text labelKey="label.gmn.ministerio.Relatorio.crud.brochuras" property="brochuras" mask="I"
			size="10" maxlength="10" /></td>
		<td><v:text labelKey="label.gmn.ministerio.Relatorio.crud.horas" property="horas" mask="I"
			size="10" maxlength="10" /></td>
	</tr>
	<tr>
		<td><v:text labelKey="label.gmn.ministerio.Relatorio.crud.revistas" property="revistas"
			mask="I" size="10" maxlength="10" /></td>
		<td><v:text labelKey="label.gmn.ministerio.Relatorio.crud.revisitas" property="revisitas"
			mask="I" size="10" maxlength="10" /></td>
		<td><v:text labelKey="label.gmn.ministerio.Relatorio.crud.estudos" property="estudos"
			mask="I" size="10" maxlength="10" /></td>
	</tr>
	<tr>
		<td colspan="3"><v:select labelKey="label.gmn.ministerio.Relatorio.crud.tipoMinisterio"
			property="tipoMinisterio" showBlank="true" autoLoad="false" required="true" /></td>
	</tr>
</table>