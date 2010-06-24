<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="6"><fmt:message key="label.gmn.core.Congregacao.tabular.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.gmn.core.Congregacao.tabular.nome">
				<v:text
					property="nome"
					size="40"
					maxlength="60"
					validateType="STRING"
					validateMinLength="5"
					validateMaxLength="60"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="6"><fmt:message key="vulpe.total.records"/>&nbsp;<v:tabular showSize="true"/></th>
	</jsp:attribute>
</v:table>
