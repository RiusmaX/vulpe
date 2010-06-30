<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="6"><fmt:message key="label.sirci.core.TipoApontamento.tabular.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.sirci.core.TipoApontamento.tabular.descricao">
				<v:text
					property="descricao"
					size="50"
					maxlength="50"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="6"><fmt:message key="vulpe.total.records"/>&nbsp;<v:tabular showSize="true"/></th>
	</jsp:attribute>
</v:table>
