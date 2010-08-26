<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:text labelKey="label.gmn.core.Publicador.crud.nome" property="nome" size="50"
			maxlength="60" required="true" /></td>
		<td><v:select labelKey="label.gmn.core.Publicador.crud.sexo" property="sexo" showBlank="true"
			autoLoad="false" required="true" /></td>
		<td><v:select labelKey="label.gmn.core.Publicador.crud.grupo" property="grupo.id"
			items="${gruposCongregacaoSelecionada}" itemKey="id" itemLabel="nome" showBlank="true"
			autoLoad="false" required="true" /></td>
	</tr>
	<tr>
		<td colspan="3">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td width="33%"><v:checkbox labelKey="label.gmn.core.Publicador.crud.indicador" property="indicador"
					fieldValue="true" /></td>
				<td width="33%"><v:checkbox labelKey="label.gmn.core.Publicador.crud.leitor" property="leitor"
					fieldValue="true" /></td>
				<td width="33%"><v:checkbox labelKey="label.gmn.core.Publicador.crud.microfone" property="microfone"
					fieldValue="true" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>