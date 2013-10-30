<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:text labelKey="label.mmn.publications.Publication.main.code" property="code"
			mask="I" size="10" maxlength="10" required="true" /></td>
		<td><v:text labelKey="label.mmn.publications.Publication.main.name" property="name" size="60"
			maxlength="100" required="true" /></td>
	</tr>
	<tr>
		<td><v:select labelKey="label.mmn.publications.Publication.main.type"
			property="type.id" items="PublicationType" itemKey="id" itemLabel="description"
			autoLoad="true" required="true" /></td>
		<td><v:select
			labelKey="label.mmn.publications.Publication.main.classification"
			property="classification" autoLoad="false" required="true" /></td>
	</tr>
</table>