<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="8"><fmt:message key="label.vulpe.security.User.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="8"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row popupProperties="id,name,username">
			<v:column labelKey="label.vulpe.security.User.select.username" property="username" sort="true" width="20%"/>
			<v:column labelKey="label.vulpe.security.User.select.name" property="name" sort="true" width="30%"/>
			<v:column labelKey="label.vulpe.security.User.select.email" property="email" sort="true" width="40%"/>
			<v:column labelKey="label.vulpe.security.User.select.active" property="active" sort="true" booleanTo="{Yes}|{No}" width="10%"/>
		</v:row>
	</jsp:attribute>
</v:table>