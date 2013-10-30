<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<v:hidden property="id"/>
<v:selectPopup
	labelKey="label.mmn.notices.Meeting.main.president"
	property="president"
	identifier="id" description="name"
	action="/core/Member/select" popupId="presidentSelectPopup"
	popupProperties="president.id=id,president.name=name"
	size="40" popupWidth="420px"
	autocomplete="true"
	required="true"
/>
<v:date
	labelKey="label.mmn.notices.Meeting.main.date"
	property="date"
	required="true"
/>
