<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:text
	labelKey="label.gmn.core.Publicador.select.nome"
	property="nome"
	size="50"
	maxlength="60"
	required="true"
/>
<v:select
	labelKey="label.gmn.core.Publicador.select.grupo"
	property="grupo.id"
	items="Grupo"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="true"
/>
<v:select
	labelKey="label.gmn.core.Publicador.select.congregacao"
	property="congregacao.id"
	items="Congregacao"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="true"
/>
<v:selectPopup
	labelKey="label.gmn.core.Publicador.select.usuario"
	property="usuario"
	identifier="id" description="name"
	action="/security/User/select/prepare" popupId="usuarioSelectPopup"
	popupProperties="usuario.id=id,usuario.name=name"
	size="40" popupWidth="420px"
	autoComplete="true"
/>
<v:checkbox
	labelKey="label.gmn.core.Publicador.select.indicador"
	property="indicador"
	fieldValue="true"
/>
<v:checkbox
	labelKey="label.gmn.core.Publicador.select.leitor"
	property="leitor"
	fieldValue="true"
/>
<v:checkbox
	labelKey="label.gmn.core.Publicador.select.microfone"
	property="microfone"
	fieldValue="true"
/>
