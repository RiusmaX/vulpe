<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><v:text labelKey="label.mmn.publications.Publication.select.code" property="code"
			mask="I" size="10" maxlength="10" /></td>
		<td><v:text labelKey="label.mmn.publications.Publication.select.name" property="name" size="60"
			maxlength="100" /></td>
	</tr>
	<tr>
		<td colspan="2">
			<v:select
			labelKey="label.mmn.publications.Publication.select.classification"
			property="classification" showBlank="true" autoLoad="false" />
		</td>
	</tr>
</table>