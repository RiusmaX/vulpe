<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="12"><fmt:message key="label.gmn.core.Publicador.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row popupProperties="id,nome">
			<v:column
				labelKey="label.gmn.core.Publicador.select.nome"
				property="nome"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.core.Publicador.select.grupo"
				property="grupo.nome"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.core.Publicador.select.congregacao"
				property="congregacao.nome"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.core.Publicador.select.usuario"
				property="usuario.name"
				sort="true"
			/>
			<v:column
				labelKey="label.gmn.core.Publicador.select.indicador"
				property="indicador"
				booleanTo="{Yes}|{No}"
			/>
			<v:column
				labelKey="label.gmn.core.Publicador.select.leitor"
				property="leitor"
				booleanTo="{Yes}|{No}"
			/>
			<v:column
				labelKey="label.gmn.core.Publicador.select.microfone"
				property="microfone"
				booleanTo="{Yes}|{No}"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="12"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
