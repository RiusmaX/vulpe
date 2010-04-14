<%@include file="/WEB-INF/protected-jsp/common/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="6"><fmt:message key="label.vulpe.security.SecureResource.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="6"><fmt:message key="vulpe.total.records"/> ${paging.size}</th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.vulpe.security.SecureResource.select.id" property="id" width="5%" sort="true"/>
			<v:column labelKey="label.vulpe.security.SecureResource.select.resourceName" property="resourceName" sort="true"/>
		</v:row>
	</jsp:attribute>
</v:table>