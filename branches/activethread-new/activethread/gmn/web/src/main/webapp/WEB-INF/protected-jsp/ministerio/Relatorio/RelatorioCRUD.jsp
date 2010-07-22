<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:hidden property="id"/>
<v:selectPopup
	labelKey="label.gmn.ministerio.Relatorio.crud.pulicador"
	property="pulicador"
	identifier="id" description="nome"
	action="/core/Publicador/select/prepare" popupId="pulicadorSelectPopup"
	popupProperties="pulicador.id=id,pulicador.nome=nome"
	size="40" popupWidth="420px"
	autoComplete="true"
/>
<v:date
	labelKey="label.gmn.ministerio.Relatorio.crud.data"
	property="data"
/>
<v:text
	labelKey="label.gmn.ministerio.Relatorio.crud.horas"
	property="horas"
	mask="I"
	size="10"
	maxlength="0"
/>
<v:text
	labelKey="label.gmn.ministerio.Relatorio.crud.livros"
	property="livros"
	mask="I"
	size="10"
	maxlength="0"
/>
<v:text
	labelKey="label.gmn.ministerio.Relatorio.crud.revistas"
	property="revistas"
	mask="I"
	size="10"
	maxlength="0"
/>
<v:text
	labelKey="label.gmn.ministerio.Relatorio.crud.revisitas"
	property="revisitas"
	mask="I"
	size="10"
	maxlength="0"
/>
<v:text
	labelKey="label.gmn.ministerio.Relatorio.crud.estudos"
	property="estudos"
	mask="I"
	size="10"
	maxlength="0"
/>
<v:select
	labelKey="label.gmn.ministerio.Relatorio.crud.tipoMinisterio"
	property="tipoMinisterio"
	showBlank="true" autoLoad="false"
/>
