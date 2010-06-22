<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.gmn.core.Grupo.crud.publicadores.nome" align="left">
				<v:text property="nome"
					size="50"
					maxlength="60"
					required="true"
				/>
			</v:column>
			<v:column labelKey="label.gmn.core.Grupo.crud.publicadores.sexo">
				<v:select property="sexo" showBlank="true"
					required="true"
				/>
			</v:column>
			<v:column labelKey="label.gmn.core.Grupo.crud.publicadores.usuario" align="left">
				<v:selectPopup property="usuario"
					identifier="id" description="name"
					action="/security/User/select/prepare" popupId="usuarioSelectPopup"
					popupProperties="usuario.id=id,usuario.name=name"
					size="40" popupWidth="420px"
					autoComplete="true"
				/>
			</v:column>
			<v:column labelKey="label.gmn.core.Grupo.crud.publicadores.indicador" align="left">
				<v:checkbox property="indicador"
					fieldValue="true"
				/>
			</v:column>
			<v:column labelKey="label.gmn.core.Grupo.crud.publicadores.leitor" align="left">
				<v:checkbox property="leitor"
					fieldValue="true"
				/>
			</v:column>
			<v:column labelKey="label.gmn.core.Grupo.crud.publicadores.microfone" align="left">
				<v:checkbox property="microfone"
					fieldValue="true"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>
