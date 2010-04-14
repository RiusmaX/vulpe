<%@include file="/WEB-INF/protected-jsp/common/common.jsp" %>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.vulpe.security.SecureResource.crud.secureResourceRoles.role">
				<v:selectPopup labelKey="label.vulpe.security.SecureResource.crud.secureResourceRoles.role.name" property="role" identifier="id" description="name" readonly="true" action="/security/Role/select/prepare" popupId="roleSelectPopup" popupProperties="role.description=description,role.name=name,role.id=id" size="40" popupWidth="600px"/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>