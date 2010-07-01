<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:textarea
	labelKey="label.sirci.core.Apontamento.select.descricao"
	property="descricao"
	cols="50" rows="3"
	required="true"
/>
<v:select
	labelKey="label.sirci.core.Apontamento.select.tipoApontamento"
	property="tipoApontamento.id"
	items="TipoApontamento"
	itemKey="id"
	itemLabel="descricao"
	showBlank="true" autoLoad="true"
	required="true"
/>
<v:select
	labelKey="label.sirci.core.Apontamento.select.situacaoApontamento"
	property="situacaoApontamento"
	showBlank="true" autoLoad="false"
/>
<v:select
	labelKey="label.sirci.core.Apontamento.select.impactoApontamento"
	property="impactoApontamento"
	showBlank="true" autoLoad="false"
/>
