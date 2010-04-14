<%@include file="/WEB-INF/protected-jsp/common/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="9"><fmt:message key="label.vulpe.audit.Occurrence.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="9"><fmt:message key="vulpe.total.records"/> ${paging.size}</th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.vulpe.audit.Occurrence.select.id" property="id" width="5%" sort="true"/>
			<v:column labelKey="label.vulpe.audit.Occurrence.select.occurrenceType" property="occurrenceType" sort="true"/>
			<v:column labelKey="label.vulpe.audit.Occurrence.select.entity" property="entity" sort="true"/>
			<v:column labelKey="label.vulpe.audit.Occurrence.select.primaryKey" property="primaryKey" sort="true"/>
			<v:column labelKey="label.vulpe.audit.Occurrence.select.username" property="username" sort="true"/>
			<v:column labelKey="label.vulpe.audit.Occurrence.select.dateTime" property="dateTime" sort="true"/>
		</v:row>
	</jsp:attribute>
</v:table>