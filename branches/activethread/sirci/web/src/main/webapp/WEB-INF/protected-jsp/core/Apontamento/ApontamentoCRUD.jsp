<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:hidden property="id"/>
<v:text
	labelKey="label.sirci.core.Apontamento.crud.descricao"
	property="descricao"
	size="50"
	maxlength="256"
	required="true"
/>
<v:select
	labelKey="label.sirci.core.Apontamento.crud.tipoApontamento"
	property="tipoApontamento.id"
	items="TipoApontamento"
	itemKey="id"
	itemLabel="descricao"
	showBlank="true" autoLoad="true"
	required="true"
/>
<v:select
	labelKey="label.sirci.core.Apontamento.crud.situacaoApontamento"
	property="situacaoApontamento"
	showBlank="true" autoLoad="false"
/>
<v:select
	labelKey="label.sirci.core.Apontamento.crud.impactoApontamento"
	property="impactoApontamento"
	showBlank="true" autoLoad="false"
/>
<v:text
	labelKey="label.sirci.core.Apontamento.crud.recomendacao"
	property="recomendacao"
	size="100"
	maxlength="2.048"
	required="true"
/>
