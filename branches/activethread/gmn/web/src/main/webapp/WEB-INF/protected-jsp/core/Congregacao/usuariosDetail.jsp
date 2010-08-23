<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.gmn.core.Congregacao.crud.usuarios.usuario" align="left">
				<v:selectPopup property="usuario"
					identifier="id" description="name"
					action="/security/User/select" popupId="usuarioSelectPopup"
					popupProperties="usuario.id=id,usuario.name=name"
					size="40" popupWidth="420px"
					autoComplete="true"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>
