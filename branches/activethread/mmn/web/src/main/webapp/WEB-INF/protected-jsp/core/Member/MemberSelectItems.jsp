<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="11"><fmt:message key="label.mmn.core.Member.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row popupProperties="id,name">
			<v:column
				labelKey="label.mmn.core.Member.select.name"
				property="name"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.core.Member.select.group"
				property="group.name"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.core.Member.select.baptized"
				property="baptized"
				booleanTo="{Yes}|{No}"
			/>
			<v:column
				labelKey="label.mmn.core.Member.select.ministryType"
				property="ministryType"
			/>
			<v:column
				labelKey="label.mmn.core.Member.select.responsibility"
				property="responsibility"
			/>
			<v:column
				labelKey="label.mmn.core.Member.select.additionalPrivileges"
				property="additionalPrivileges" enumType="AdditionalPrivilege"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="11"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
