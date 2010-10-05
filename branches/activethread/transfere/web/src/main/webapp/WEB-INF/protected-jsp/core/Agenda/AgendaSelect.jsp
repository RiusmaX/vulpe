<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<table>
	<tr>
		<td><v:date labelKey="label.transfere.core.Agenda.select.dataAgendamento"
			property="dataAgendamento" /></td>
		<td><v:select labelKey="label.transfere.core.Agenda.select.destino" property="destino"
			showBlank="true" autoLoad="false" /></td>
		<td><v:select labelKey="label.transfere.core.Agenda.select.status" property="status"
			showBlank="true" autoLoad="false" /></td>
	</tr>
</table>