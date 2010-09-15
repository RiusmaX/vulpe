<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:text labelKey="label.gmn.core.Publicador.crud.nome" property="nome" size="40"
			maxlength="60" required="true" /></td>
		<td><v:select labelKey="label.gmn.core.Publicador.crud.sexo" property="sexo"
			showBlank="false" autoLoad="false"
			onchange="app.core.privilegios('${vulpeFormName}_entity.batizado', this.id)" /></td>
		<td><v:select labelKey="label.gmn.core.Publicador.crud.grupo" property="grupo.id"
			items="${gruposCongregacaoSelecionada}" itemKey="id" itemLabel="nome" showBlank="false"
			autoLoad="false" /></td>
	</tr>
	<tr>
		<td><v:checkbox labelKey="label.gmn.core.Publicador.crud.batizado" property="batizado"
			fieldValue="true" onclick="app.core.privilegios(this.id, '${vulpeFormName}_entity.sexo')" /></td>
		<td colspan="2"><span id="tipoMinisterio" style="${entity.batizado ? '' : 'display: none'}"><v:select
			labelKey="label.gmn.core.Publicador.crud.tipoMinisterio" property="tipoMinisterio"
			showBlank="false" autoLoad="false" items="${listaTipoMinisterio}" /></span> <span
			id="tipoMinisterioSimples"><v:select
			labelKey="label.gmn.core.Publicador.crud.tipoMinisterio" property="tipoMinisterioSimples"
			showBlank="false" autoLoad="false" items="${listaTipoMinisterioSimples}" /></span></td>
	</tr>
	<tr>
		<td colspan="3"><span id="privilegio"
			style="${entity.batizado && entity.sexo == 'MASCULINO' ? '' : 'display: none'}"><v:select
			labelKey="label.gmn.core.Publicador.crud.cargo" property="cargo" showBlank="true"
			autoLoad="false" onchange="app.core.privilegiosAdicionais(this)" /></span></td>
	</tr>
	<tr>
		<td colspan="3">
		<fieldset id="privilegioAdicional"
			style="${entity.batizado && entity.sexo == 'MASCULINO' ? '' : 'display: none'}"><legend><v:label
			key="label.gmn.core.Publicador.crud.privilegiosAdicionais" /></legend> <v:checkboxlist
			property="privilegiosAdicionais" enumeration="PrivilegioAdicional" /></fieldset>
		</td>
	</tr>
</table>
<c:if test="${empty entity.cargo}">
<script type="text/javascript">
jQuery(function(){
	app.core.mostrarOcultarPrivilegiosAdicionais(4, false);
});
</script>
</c:if>