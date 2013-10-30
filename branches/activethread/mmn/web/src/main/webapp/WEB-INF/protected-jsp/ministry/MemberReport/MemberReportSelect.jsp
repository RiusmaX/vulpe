<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<div class="line">
<v:select labelKey="label.mmn.ministry.MemberReport.select.month" property="month"
			showBlank="true" autoLoad="false" />
<v:select labelKey="label.mmn.ministry.MemberReport.select.ministryType"
			property="ministryType" autoLoad="false" showBlank="true" />
<v:selectPopup labelKey="label.mmn.ministry.MemberReport.select.member" property="member"
			identifier="id" description="name" action="/core/Member/select" popupId="memberSelectPopup"
			popupProperties="member.id=id,member.name=name" size="40" popupWidth="600px" autocomplete="true"
			autocompleteMinLength="1" autocompleteValueList="${ever['membersAutocompleteValueList']}" />
</div>