<%@include file="/WEB-INF/protected-jsp/common/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="4"><fmt:message key="label.vulpe.security.Role.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="4"><fmt:message key="vulpe.total.records"/> ${fn:length(entities)}</th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.vulpe.security.Role.select.name">
				<v:text property="name" size="40" upperCase="true"/>
			</v:column>
			<v:column labelKey="label.vulpe.security.Role.select.description">
				<v:text property="description" size="60"/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>