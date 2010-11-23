<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.vulpe.security.User.main.userRoles.role">
				<v:selectPopup labelKey="label.vulpe.security.User.main.userRoles.role.name" property="role"
					identifier="id" description="description" action="/security/Role/select"
					popupId="roleSelectPopup"
					popupProperties="role.description=description,role.name=name,role.id=id" size="60"
					popupWidth="600px" autocomplete="true" />
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>