<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:text labelKey="label.gmn.core.Publicador.select.nome" property="nome" size="50"
			maxlength="60" required="true" /></td>
		<td><v:select labelKey="label.gmn.core.Publicador.select.sexo" property="sexo" showBlank="true"
			autoLoad="false" required="true" /></td>
		<td><v:select labelKey="label.gmn.core.Publicador.select.grupo" property="grupo.id"
			items="${gruposCongregacaoSelecionada}" itemKey="id" itemLabel="nome" showBlank="true"
			autoLoad="false"/></td>
	</tr>
</table>
<%--
<v:checkbox
	labelKey="label.gmn.core.Publicador.select.indicador"
	property="indicador"
	fieldValue="true"
/>
<v:checkbox
	labelKey="label.gmn.core.Publicador.select.leitor"
	property="leitor"
	fieldValue="true"
/>
<v:checkbox
	labelKey="label.gmn.core.Publicador.select.microfone"
	property="microfone"
	fieldValue="true"
/>
 --%>