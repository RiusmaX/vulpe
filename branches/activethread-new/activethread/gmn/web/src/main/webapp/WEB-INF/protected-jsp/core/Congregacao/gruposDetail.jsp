<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.gmn.core.Congregacao.crud.grupos.nome" align="left">
				<v:text property="nome"
					size="40"
					maxlength="40"
					validateType="STRING"
					validateMinLength="5"
					validateMaxLength="40"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>
