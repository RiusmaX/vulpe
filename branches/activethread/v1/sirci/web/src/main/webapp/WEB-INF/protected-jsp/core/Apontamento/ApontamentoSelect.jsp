<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<table>
<tr>
<td width="10%">
<v:select
	labelKey="label.sirci.core.Apontamento.select.situacaoApontamento"
	property="situacaoApontamento"
	showBlank="true" autoLoad="false" headerKey="label.sirci.selecao.todas"
/>
</td>
<td>
<v:select
	labelKey="label.sirci.core.Apontamento.select.impactoApontamento"
	property="impactoApontamento"
	showBlank="true" autoLoad="false" headerKey="label.sirci.selecao.todos"
/>
</td>
</tr>
</table>