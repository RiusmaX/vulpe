<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="9"><fmt:message key="label.vulpe.commons.Menu.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="9"><fmt:message key="vulpe.total.records"/> ${paging.size}</th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row popupProperties="id,name,username">
			<v:column labelKey="label.vulpe.commons.Menu.select.id" property="id" width="5%" sort="true"/>
			<v:column labelKey="label.vulpe.commons.Menu.select.name" property="name" sort="true"/>
			<v:column labelKey="label.vulpe.commons.Menu.select.description" property="description" sort="true"/>
			<v:column labelKey="label.vulpe.commons.Menu.select.backendOnly" property="backendOnly" sort="true" booleanTo="{Yes}|{No"/>
		</v:row>
	</jsp:attribute>
</v:table>