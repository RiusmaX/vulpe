<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="11"><fmt:message key="label.transfere.core.Agenda.select.header" /></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row showUpdateButton="${currentItem.status == 'ABERTO'}">
			<v:column labelKey="label.transfere.core.Agenda.select.dataAgendamento"
				property="dataHoraAgendamento" sort="true" sortProperty="dataAgendamento" />
			<v:column labelKey="label.transfere.core.Agenda.select.destino" property="destino" sort="true" />
			<v:column labelKey="label.transfere.core.Agenda.select.tarefa" property="tarefa" sort="true" />
			<v:column labelKey="label.transfere.core.Agenda.select.status" property="status" sort="true" />
			<v:column labelKey="label.transfere.core.Agenda.select.dataTermino" property="dataHoraTermino"
				sort="true" sortProperty="dataTermino" />
			<v:column labelKey="label.transfere.core.Agenda.select.acoes">
				<v:action elementId="Autoriza${currentStatus.count}"
					labelKey="label.transfere.core.Agenda.select.autorizar" javascript="app.all.autorizarAgendamento(${currentItem.id})"
					showButtonAsImage="false" show="${currentItem.status == 'ABERTO'}" />
				<v:action elementId="Cancelar${currentStatus.count}"
					labelKey="label.transfere.core.Agenda.select.cancelar" javascript="app.all.cancelarAgendamento(${currentItem.id})"
					showButtonAsImage="false" show="${currentItem.status == 'AUTORIZADO'}" />
				<v:action elementId="Reiniciar${currentStatus.count}"
					labelKey="label.transfere.core.Agenda.select.reiniciar" javascript="app.all.reiniciarAgendamento(${currentItem.id})"
					showButtonAsImage="false" show="${currentItem.status == 'CANCELADO' || currentItem.status == 'FINALIZADO' || currentItem.status == 'FINALIZADO_COM_ERRO'}" />
			</v:column>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="11"><fmt:message key="vulpe.total.records" />&nbsp;<v:paging showSize="true" /></th>
	</jsp:attribute>
</v:table>
