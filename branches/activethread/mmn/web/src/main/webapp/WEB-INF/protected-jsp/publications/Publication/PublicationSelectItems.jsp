<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="9"><fmt:message key="label.mmn.publications.Publication.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row popupProperties="id,code,name">
			<v:column
				labelKey="label.mmn.publications.Publication.select.code"
				property="code"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.publications.Publication.select.name"
				property="name"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.publications.Publication.select.type"
				property="type.description"
				sort="true"
			/>
			<v:column
				labelKey="label.mmn.publications.Publication.select.classification"
				property="classification"
				sort="true"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="9"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
