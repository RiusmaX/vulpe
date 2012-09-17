<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row popupProperties="id,name,description">
			<v:column labelKey="label.vulpe.security.Role.select.name" property="simpleName" sort="true" sortProperty="name" width="30%"/>
			<v:column labelKey="label.vulpe.security.Role.select.description" property="description" sort="true" width="70%"/>
		</v:row>
	</jsp:attribute>
</v:table>