<%@include file="/WEB-INF/protected-jsp/common/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="9"><fmt:message key="label.vulpe.security.User.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="9"><fmt:message key="vulpe.total.records"/> ${paging.size}</th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row popupProperties="id,name,username">
			<v:column labelKey="label.vulpe.security.User.select.id" property="id" width="5%" sort="true"/>
			<v:column labelKey="label.vulpe.security.User.select.username" property="username" sort="true"/>
			<v:column labelKey="label.vulpe.security.User.select.name" property="name" sort="true"/>
			<v:column labelKey="label.vulpe.security.User.select.email" property="email" sort="true"/>
			<v:column labelKey="label.vulpe.security.User.select.active" property="active" sort="true" booleanTo="{vulpe.label.true.yes}|{vulpe.label.false.no"/>
		</v:row>
	</jsp:attribute>
</v:table>