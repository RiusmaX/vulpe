<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="11"><fmt:message key="label.gmn.core.Publicador.select.header"/></th>
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
				labelKey="label.gmn.core.Publicador.select.batizado"
				property="batizado"
				booleanTo="{Yes}|{No}"
			/>
			<v:column
				labelKey="label.gmn.core.Publicador.select.tipoMinisterio"
				property="tipoMinisterio"
			/>
			<v:column
				labelKey="label.gmn.core.Publicador.select.cargo"
				property="cargo"
			/>
			<v:column
				labelKey="label.gmn.core.Publicador.select.privilegiosAdicionais"
				property="privilegiosAdicionais" enumType="PrivilegioAdicional"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="11"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
