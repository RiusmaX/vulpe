<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:text labelKey="label.gmn.publicacoes.Publicacao.crud.codigo" property="codigo"
			mask="I" size="10" maxlength="10" /></td>
		<td><v:text labelKey="label.gmn.publicacoes.Publicacao.crud.nome" property="nome" size="40"
			maxlength="40" required="true" /></td>
	</tr>
	<tr>
		<td><v:select labelKey="label.gmn.publicacoes.Publicacao.crud.tipoPublicacao"
			property="tipoPublicacao.id" items="TipoPublicacao" itemKey="id" itemLabel="descricao"
			showBlank="true" autoLoad="true" required="true" /></td>
		<td><v:select
			labelKey="label.gmn.publicacoes.Publicacao.crud.classificacaoPublicacao"
			property="classificacaoPublicacao" showBlank="true" autoLoad="false" required="true" /></td>
	</tr>
</table>