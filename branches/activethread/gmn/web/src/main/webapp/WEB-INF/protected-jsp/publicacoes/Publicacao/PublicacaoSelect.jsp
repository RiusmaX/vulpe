<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:text labelKey="label.gmn.publicacoes.Publicacao.select.codigo" property="codigo"
			mask="I" size="10" maxlength="10" /></td>
		<td><v:text labelKey="label.gmn.publicacoes.Publicacao.select.nome" property="nome" size="60"
			maxlength="100" /></td>
	</tr>
	<tr>
		<td colspan="2">
			<v:select
			labelKey="label.gmn.publicacoes.Publicacao.select.classificacao"
			property="classificacao" showBlank="true" autoLoad="false" />
		</td>
	</tr>
</table>