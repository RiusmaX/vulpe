<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<div id="report"></div>
<c:if test="${not empty entity.id}"><div id="control"><input type="hidden" name="entitySelect.id" value="${entity.id}"></div></c:if>
<v:hidden property="id" />
<v:hidden property="member.id" />
<v:hidden property="date" />
<table id="reportData">
	<tr>
		<td><v:select labelKey="label.mmn.ministry.MemberPersonalReport.main.year" property="year"
			showBlank="true" autoLoad="false" items="${ever['years']}" itemKey="number" itemLabel="number" onchange="app.ministry.changePersonalReportData($(this).val());" /></td>
		<td><v:select labelKey="label.mmn.ministry.MemberPersonalReport.main.month" property="month"
			showBlank="true" autoLoad="false" onchange="app.ministry.changePersonalReportData($(this).val());" /></td>
		<td><v:select labelKey="label.mmn.ministry.MemberPersonalReport.main.ministryType"
			property="ministryType" showBlank="true" autoLoad="false" /></td>
		<td><v:text labelKey="label.mmn.ministry.MemberPersonalReport.main.studies"
			property="studies" mask="I" size="8" /></td>
	</tr>
</table>