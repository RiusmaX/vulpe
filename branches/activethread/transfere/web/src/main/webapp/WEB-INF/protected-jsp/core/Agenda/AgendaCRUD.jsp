<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:date
	labelKey="label.transfere.core.Agenda.crud.dataAgendamento"
	property="dataAgendamento"
/>
<v:select
	labelKey="label.transfere.core.Agenda.crud.destino"
	property="destino"
	showBlank="true" autoLoad="false"
/>
<v:textarea
	labelKey="label.transfere.core.Agenda.crud.tarefa"
	property="tarefa"
	cols="80" rows="3"
/>
<v:select
	labelKey="label.transfere.core.Agenda.crud.status"
	property="status"
	showBlank="true" autoLoad="false"
/>
<v:selectPopup
	labelKey="label.transfere.core.Agenda.crud.usuario"
	property="usuario"
	identifier="id" description="name"
	action="/security/User/select" popupId="usuarioSelectPopup"
	popupProperties="usuario.id=id,usuario.name=name"
	size="40" popupWidth="420px"
	autocomplete="true"
/>
<v:selectPopup
	labelKey="label.transfere.core.Agenda.crud.objeto"
	property="objeto"
	identifier="id" description="descricao"
	action="/core/Objeto/select" popupId="objetoSelectPopup"
	popupProperties="objeto.id=id,objeto.descricao=descricao"
	size="40" popupWidth="420px"
	autocomplete="true"
/>
