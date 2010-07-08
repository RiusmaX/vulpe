<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:hidden property="id"/>
<table>
<tr>
<td colspan="2">
<v:textarea
	labelKey="label.sirci.core.Apontamento.crud.descricao"
	property="descricao"
	cols="50" rows="3"
	required="true"
/>
</td>
</tr>
<tr>
<td colspan="2">
<v:select
	labelKey="label.sirci.core.Apontamento.crud.tipoApontamento"
	property="tipoApontamento.id"
	items="TipoApontamento"
	itemKey="id"
	itemLabel="descricao"
	showBlank="true" autoLoad="true"
	required="true"
/>
</td>
</tr>
<tr>
<td>
<v:select
	labelKey="label.sirci.core.Apontamento.crud.orgaoOrigem"
	property="orgaoOrigem.id"
	items="OrgaoOrigem"
	itemKey="id"
	itemLabel="descricao"
	showBlank="true" autoLoad="true"
	required="true" onchange="app.carregarDocumentos('${vulpeFormName}')"
/>
</td>
<td>
<div id="documentos">
<v:select
	labelKey="label.sirci.core.Apontamento.crud.documentoOrigem"
	property="documentoOrigem.id"
	items="${listaDocumentoOrigem}"
	itemKey="id" size="3"
	itemLabel="descricao"
	required="true"
/>
</div>
</td>
</tr>
<tr>
<td>
<v:select
	labelKey="label.sirci.core.Apontamento.crud.unidadeResponsavel"
	property="unidadeResponsavel.id"
	items="Unidade"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="true"
	required="true"
/>
</td><td>
<v:select
	labelKey="label.sirci.core.Apontamento.crud.unidadeCoresponsavel"
	property="unidadeCoresponsavel.id"
	items="Unidade"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="true"
	required="true"
/>
</td>
</tr>
<tr>
<td>
<v:select
	labelKey="label.sirci.core.Apontamento.crud.situacaoApontamento"
	property="situacaoApontamento"
	showBlank="true" autoLoad="false"
/>
</td>
<td>
<v:select
	labelKey="label.sirci.core.Apontamento.crud.impactoApontamento"
	property="impactoApontamento"
	showBlank="true" autoLoad="false"
/>
</td>
</tr>
<tr>
<td colspan="2">
<v:textarea
	labelKey="label.sirci.core.Apontamento.crud.recomendacao"
	property="recomendacao"
	cols="50" rows="3"
	required="true"
/>
</td>
</tr>
</table>