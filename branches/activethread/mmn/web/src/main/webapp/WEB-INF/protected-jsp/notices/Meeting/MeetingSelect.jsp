<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<v:selectPopup
	labelKey="label.mmn.notices.Meeting.select.president"
	property="president"
	identifier="id" description="name"
	action="/core/Member/select" popupId="presidentSelectPopup"
	popupProperties="president.id=id,president.name=name"
	size="40" popupWidth="420px"
	autocomplete="true"
/>
