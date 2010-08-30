<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.gmn.anuncios.EscolaMinisterio.crud.discursos.publicador">
				<v:selectPopup property="publicador"
					identifier="id" description="nome"
					action="/core/Publicador/select" popupId="publicadorSelectPopup"
					popupProperties="publicador.id=id,publicador.nome=nome"
					size="40" popupWidth="420px"
					autoComplete="true"
					required="true"
				/>
			</v:column>
			<v:column labelKey="label.gmn.anuncios.EscolaMinisterio.crud.discursos.tema">
				<v:text property="tema"
					size="40"
					maxlength="100"
					required="true"
				/>
			</v:column>
			<v:column labelKey="label.gmn.anuncios.EscolaMinisterio.crud.discursos.materia">
				<v:text property="materia"
					size="40"
					maxlength="60"
					required="true"
				/>
			</v:column>
			<v:column labelKey="label.gmn.anuncios.EscolaMinisterio.crud.discursos.tipoDiscurso">
				<v:select property="tipoDiscurso"
					showBlank="true" autoLoad="false"
					required="true"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>
