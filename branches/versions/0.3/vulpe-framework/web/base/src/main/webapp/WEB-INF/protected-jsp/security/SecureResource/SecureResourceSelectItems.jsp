<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="6"><fmt:message key="label.vulpe.security.SecureResource.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="6"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.vulpe.security.SecureResource.select.id" property="id" width="5%" sort="true"/>
			<v:column labelKey="label.vulpe.security.SecureResource.select.resourceName" property="resourceName" sort="true"/>
		</v:row>
	</jsp:attribute>
</v:table>