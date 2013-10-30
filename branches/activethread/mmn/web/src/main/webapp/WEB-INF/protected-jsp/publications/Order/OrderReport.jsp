<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:selectPopup
	labelKey="label.mmn.publications.Order.select.member"
	property="member"
	identifier="id" description="name"
	action="/core/Member/select" popupId="memberSelectPopup"
	popupProperties="member.id=id,member.name=name"
	size="40" popupWidth="420px" autocomplete="true"
/>
<v:checkbox
	labelKey="label.mmn.publications.Order.select.delivered"
	property="delivered"
	fieldValue="true"
/>
