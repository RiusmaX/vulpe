<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:text labelKey="label.gmn.publicacoes.Publicacao.select.codigo" property="codigo"
			mask="I" size="10" maxlength="10" /></td>
		<td><v:text labelKey="label.gmn.publicacoes.Publicacao.select.nome" property="nome" size="40"
			maxlength="40" required="true" /></td>
	</tr>
</table>