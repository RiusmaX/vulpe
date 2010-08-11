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
				<v:text property="nomeObjeto" upperCase="true"
					size="60"
				/>
			</v:column>
			<v:column labelKey="label.sitra.core.Objeto.crud.objetoItens.status">
				<v:hidden property="status" />
				<v:show type="enum" property="status"/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>
