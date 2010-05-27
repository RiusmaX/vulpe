<%@include file="/WEB-INF/protected-jsp/common/common.jsp"%>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.vulpe.security.User.crud.userRoles.role">
				<v:selectPopup labelKey="label.vulpe.security.User.crud.userRoles.role.name" property="role"
					identifier="id" description="description" readonly="true"
					action="/security/Role/select/prepare" popupId="roleSelectPopup"
					popupProperties="role.description=description,role.name=name,role.id=id" size="40"
					popupWidth="600px" autoComplete="true" required="true" />
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>