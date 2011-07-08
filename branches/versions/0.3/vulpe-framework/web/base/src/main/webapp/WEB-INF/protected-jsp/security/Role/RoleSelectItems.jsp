<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row popupProperties="id,name,description">
			<v:column labelKey="label.vulpe.security.Role.select.id" property="id" width="5%" sort="true"/>
			<v:column labelKey="label.vulpe.security.Role.select.name" property="name" sort="true"/>
			<v:column labelKey="label.vulpe.security.Role.select.description" property="description" sort="true"/>
		</v:row>
	</jsp:attribute>
</v:table>