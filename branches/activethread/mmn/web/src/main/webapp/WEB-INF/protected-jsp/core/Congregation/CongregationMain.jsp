<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="2"><v:text labelKey="label.mmn.core.Congregation.main.name" property="name"
			required="true" size="40" maxlength="60" validateType="STRING" validateMinLength="5"
			validateMaxLength="60" /></td>
	</tr>
	<tr>
		<td><v:select labelKey="label.mmn.core.Congregation.main.firstMeetingDay"
			property="firstMeetingDay" required="true" showBlank="true" /></td>
		<td><v:text labelKey="label.mmn.core.Congregation.main.firstMeetingHour"
			property="firstMeetingHour" size="5" maxlength="5" mask="99:99" required="true" /></td>
	</tr>
	<tr>
		<td><v:select labelKey="label.mmn.core.Congregation.main.secondMeetingDay"
			property="secondMeetingDay" required="true" showBlank="true" /></td>
		<td><v:text labelKey="label.mmn.core.Congregation.main.secondMeetingHour"
			property="secondMeetingHour" size="5" maxlength="5" mask="99:99" required="true" /></td>
	</tr>
</table>