<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:menu elementId="Index" labelKey="label.mmn.menu.Index" roles="ADMINISTRATOR,PUBLICATIONS,MINISTRY,NOTICES">
	<v:menu elementId="SelectCongregation" labelKey="label.mmn.menu.Index.selectCongregation" action="/backend/Index" />
	<v:menu elementId="Congregation" labelKey="label.mmn.menu.Index.congregations" action="/core/Congregation/select" roles="ADMINISTRATOR" />
	<v:menu elementId="Member" labelKey="label.mmn.menu.Index.members" action="/core/Member/select" roles="ADMINISTRATOR,MINISTRY" />
</v:menu>
<v:menu elementId="Publications" labelKey="label.mmn.menu.Publications" roles="ADMINISTRATOR,PUBLICATIONS">
	<v:menu elementId="PublicationType" labelKey="label.mmn.menu.Publications.publicationType"
		action="/publications/PublicationType/tabular" />
	<v:menu elementId="Publication" labelKey="label.mmn.menu.Publications.publications"
		action="/publications/Publication/select" />
	<v:menu elementId="Order" labelKey="label.mmn.menu.Publications.orders" action="/publications/Order/select" />
</v:menu>
<v:menu elementId="Ministry" labelKey="label.mmn.menu.Ministry" roles="ADMINISTRATOR,MINISTRY">
	<v:menu elementId="MemberReport" labelKey="label.mmn.menu.Ministry.memberReport" action="/ministry/MemberReport/select" />
	<v:menu elementId="PersonalMemberReport" labelKey="label.mmn.menu.Ministry.memberPersonalReport" action="/ministry/MemberPersonalReport/update" roles="ADMINISTRATOR" />
</v:menu>
<v:menu elementId="Notices" labelKey="label.mmn.menu.Notices" roles="ADMINISTRATOR,NOTICES">
	<v:menu elementId="SchoolMinistry" labelKey="label.mmn.menu.Notices.schoolMinistry" action="/notices/Meeting/select" />
	<v:menu elementId="ServiceMeeting" labelKey="label.mmn.menu.Notices.seviceMeeting" action="/notices/Meeting/select" />
</v:menu>