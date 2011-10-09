<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<v:hidden property="date" />
<div class="line">
<v:select labelKey="label.mmn.ministry.MemberReport.main.month" property="month"
			showBlank="true" autoLoad="false" required="true" />
<v:select labelKey="label.mmn.ministry.MemberReport.main.ministryType"
			property="ministryType" autoLoad="false" required="true" />
<v:selectPopup labelKey="label.mmn.ministry.MemberReport.main.member"
			autocompleteMinLength="1" property="member" identifier="id" description="name"
			action="/core/Member/select" popupId="memberSelectPopup"
			popupProperties="member.id=id,member.name=name" size="40" popupWidth="600px" autocomplete="true"
			autocompleteValueList="${ever['membersAutocompleteValueList']}" required="true" autocompleteProperties="ministryType" />
</div><br/>
<div class="line">
<v:text labelKey="label.mmn.ministry.MemberReport.main.books" property="books" mask="I" size="8" maxlength="8" />
<v:text labelKey="label.mmn.ministry.MemberReport.main.brochures" property="brochures" mask="I" size="8" maxlength="8" />
<v:text labelKey="label.mmn.ministry.MemberReport.main.hours" property="hours" mask="I" size="8" maxlength="10" style="background-color: #FFFFDD" />
<v:text labelKey="label.mmn.ministry.MemberReport.main.magazines" property="magazines" mask="I" size="8" maxlength="8" />
<v:text labelKey="label.mmn.ministry.MemberReport.main.revisits" property="revisits" mask="I" size="8" maxlength="8" />
<v:text labelKey="label.mmn.ministry.MemberReport.main.studies" property="studies" mask="I" size="8" maxlength="8" />
</div>