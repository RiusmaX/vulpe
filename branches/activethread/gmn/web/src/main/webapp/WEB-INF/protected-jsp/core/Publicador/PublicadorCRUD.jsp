<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:hidden property="id"/>
<v:text
	labelKey="label.gmn.core.Publicador.crud.nome"
	property="nome"
	size="50"
	maxlength="60"
	required="true"
/>
<v:select
	labelKey="label.gmn.core.Publicador.crud.sexo"
	property="sexo"
	showBlank="true" autoLoad="false"
	required="true"
/>
<v:select
	labelKey="label.gmn.core.Publicador.crud.grupo"
	property="grupo.id"
	items="Grupo"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="true"
/>
<v:checkbox
	labelKey="label.gmn.core.Publicador.crud.indicador"
	property="indicador"
	fieldValue="true"
/>
<v:checkbox
	labelKey="label.gmn.core.Publicador.crud.leitor"
	property="leitor"
	fieldValue="true"
/>
<v:checkbox
	labelKey="label.gmn.core.Publicador.crud.microfone"
	property="microfone"
	fieldValue="true"
/>
