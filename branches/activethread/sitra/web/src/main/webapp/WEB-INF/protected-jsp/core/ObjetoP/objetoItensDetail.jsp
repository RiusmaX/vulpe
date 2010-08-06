<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.sitra.core.Objeto.crud.objetoItens.tipoObjeto">
				<v:select property="tipoObjeto"
					showBlank="false" autoLoad="false"
				/>
			</v:column>
			<v:column labelKey="label.sitra.core.Objeto.crud.objetoItens.nomeObjeto">
				<v:text property="nomeObjeto"
					size="40"
				/>
			</v:column>
			<v:column labelKey="label.sitra.core.Objeto.crud.objetoItens.status">
				<v:select property="status"
					showBlank="false" autoLoad="false" disabled="true"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>
