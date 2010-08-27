<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="6"><fmt:message key="label.gmn.publicacoes.TipoPublicacao.tabular.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.gmn.publicacoes.TipoPublicacao.tabular.descricao">
				<v:text
					property="descricao"
					size="50"
					maxlength="100"
					validateType="STRING"
					validateMinLength="3"
					validateMaxLength="100"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="6"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
