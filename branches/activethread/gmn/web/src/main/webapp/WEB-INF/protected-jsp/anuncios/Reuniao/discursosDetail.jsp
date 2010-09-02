<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.gmn.anuncios.ReuniaoServico.crud.discursos.orador">
				<v:selectPopup property="orador"
					identifier="id" description="nome"
					action="/core/Publicador/select" popupId="oradorSelectPopup"
					popupProperties="orador.id=id,orador.nome=nome"
					size="40" popupWidth="420px"
					autoComplete="true"
					required="true"
				/>
			</v:column>
			<v:column labelKey="label.gmn.anuncios.ReuniaoServico.crud.discursos.tema">
				<v:text property="tema"
					size="40"
					maxlength="100"
					required="true"
				/>
			</v:column>
			<v:column labelKey="label.gmn.anuncios.ReuniaoServico.crud.discursos.tempo">
				<v:text property="tempo"
					mask="I"
					required="true"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>
