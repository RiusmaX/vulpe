<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.mmn.core.Congregation.main.users.user" align="left">
				<v:selectPopup property="user"
					identifier="id" description="name"
					action="/security/User/select" popupId="userSelectPopup"
					popupProperties="user.id=id,user.name=name"
					size="40" popupWidth="420px"
					autocomplete="true"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>
