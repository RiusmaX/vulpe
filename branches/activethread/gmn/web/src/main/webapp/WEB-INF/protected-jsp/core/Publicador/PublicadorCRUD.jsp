<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:hidden property="id"/>
<v:text
	labelKey="label.gmn.core.Publicador.crud.nome"
	property="nome"
	size="50"
	maxlength="60"
	required="true"
/>
<v:radio
	labelKey="label.gmn.core.Publicador.crud.sexo"
	property="sexo"
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
<v:select
	labelKey="label.gmn.core.Publicador.crud.congregacao"
	property="congregacao.id"
	items="Congregacao"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="true"
/>
<v:selectPopup
	labelKey="label.gmn.core.Publicador.crud.usuario"
	property="usuario"
	identifier="id" description="name"
	action="/security/User/select/prepare" popupId="usuarioSelectPopup"
	popupProperties="usuario.id=id,usuario.name=name"
	size="40" popupWidth="420px"
	autoComplete="true"
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
