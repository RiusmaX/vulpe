<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:text labelKey="label.gmn.core.Publicador.select.nome" property="nome" size="30"
			maxlength="60" /></td>
		<td><v:select labelKey="label.gmn.core.Publicador.select.sexo" property="sexo"
			showBlank="true" autoLoad="false" /></td>
		<td><v:select labelKey="label.gmn.core.Publicador.select.grupo" property="grupo.id"
			items="${gruposCongregacaoSelecionada}" itemKey="id" itemLabel="nome" showBlank="true"
			autoLoad="false" /></td>
		<td><v:select labelKey="label.gmn.core.Publicador.select.cargo" property="cargo"
			showBlank="true" autoLoad="false" /></td>
	</tr>
	<tr>
		<td colspan="4"><v:checkboxlist labelKey="label.gmn.core.Publicador.select.privilegiosAdicionais" property="privilegiosAdicionais" enumeration="PrivilegioAdicional" /></td>
	</tr>
</table>