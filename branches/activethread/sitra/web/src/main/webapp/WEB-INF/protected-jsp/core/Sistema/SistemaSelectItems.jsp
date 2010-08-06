<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="8"><fmt:message key="label.sitra.core.Sistema.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.sitra.core.Sistema.select.nome"
				property="nome"
			/>
			<v:column
				labelKey="label.sitra.core.Sistema.select.sigla"
				property="sigla"
			/>
			<v:column
				labelKey="label.sitra.core.Sistema.select.gerencia"
				property="gerencia"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="8"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
