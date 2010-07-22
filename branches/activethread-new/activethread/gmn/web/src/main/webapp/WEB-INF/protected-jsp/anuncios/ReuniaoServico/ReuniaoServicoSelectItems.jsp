<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="7"><fmt:message key="label.gmn.anuncios.ReuniaoServico.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.gmn.anuncios.ReuniaoServico.select.presidente"
				property="presidente.nome"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.anuncios.ReuniaoServico.select.data"
				property="data"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="7"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
