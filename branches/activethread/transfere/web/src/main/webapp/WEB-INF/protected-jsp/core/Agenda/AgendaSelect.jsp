<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:date
	labelKey="label.transfere.core.Agenda.select.dataAgendamento"
	property="dataAgendamento"
/>
<v:select
	labelKey="label.transfere.core.Agenda.select.destino"
	property="destino"
	showBlank="true" autoLoad="false"
/>
<v:select
	labelKey="label.transfere.core.Agenda.select.status"
	property="status"
	showBlank="true" autoLoad="false"
/>