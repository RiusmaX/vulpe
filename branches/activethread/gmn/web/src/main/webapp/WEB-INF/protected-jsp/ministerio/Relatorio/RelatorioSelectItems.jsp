<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="8"><fmt:message key="label.gmn.ministerio.Relatorio.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.gmn.ministerio.Relatorio.select.data"
				property="data"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.ministerio.Relatorio.select.publicador"
				property="publicador.nome"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.ministerio.Relatorio.select.horas"
				property="horas"
				sort="true"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="8"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
