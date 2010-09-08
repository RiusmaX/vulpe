<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:text labelKey="label.gmn.core.Publicador.crud.nome" property="nome" size="40"
			maxlength="60" required="true" /></td>
		<td><v:select labelKey="label.gmn.core.Publicador.crud.sexo" property="sexo" showBlank="false"
			autoLoad="false"
			onchange="app.privilegios('${vulpeFormName}_entity.batizado', this.id)" /></td>
		<td><v:select labelKey="label.gmn.core.Publicador.crud.grupo" property="grupo.id"
			items="${gruposCongregacaoSelecionada}" itemKey="id" itemLabel="nome" showBlank="false"
			autoLoad="false" /></td>
	</tr>
	<tr>
		<td colspan="3"><v:checkbox labelKey="label.gmn.core.Publicador.crud.batizado" property="batizado"
			fieldValue="true" onclick="app.privilegios(this.id, '${vulpeFormName}_entity.sexo')" /></td>
	</tr>
	<tr>
		<td><span id="tipoMinisterio" style="${entity.batizado ? '' : 'display: none'}"><v:select
			labelKey="label.gmn.core.Publicador.crud.tipoMinisterio" property="tipoMinisterio"
			showBlank="false" autoLoad="false" /></span></td>
		<td colspan="2"><span id="privilegio" style="${entity.batizado && entity.sexo == 'MASCULINO' ? '' : 'display: none'}"><v:select labelKey="label.gmn.core.Publicador.crud.cargo"
			property="cargo" showBlank="true" autoLoad="false" /></span></td>
	</tr>
	<tr>
		<td colspan="3">
		<fieldset id="privilegioAdicional" style="${entity.batizado && entity.sexo == 'MASCULINO' ? '' : 'display: none'}"><legend>&nbsp;Privilégios
		Adicionais&nbsp;</legend>
		<v:checkboxlist property="privilegiosAdicionais" enumeration="PrivilegioAdicional"/>
		</fieldset>
		</td>
	</tr>
</table>