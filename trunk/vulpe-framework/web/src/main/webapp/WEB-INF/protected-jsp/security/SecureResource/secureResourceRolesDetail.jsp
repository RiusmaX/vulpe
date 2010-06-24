<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.vulpe.security.SecureResource.crud.secureResourceRoles.role">
				<v:selectPopup labelKey="label.vulpe.security.SecureResource.crud.secureResourceRoles.role.name"
					property="role" identifier="id" description="description" readonly="true"
					action="/security/Role/select" popupId="roleSelectPopup"
					popupProperties="role.description=description,role.name=name,role.id=id" size="40"
					popupWidth="600px" autoComplete="true" required="true" />
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>