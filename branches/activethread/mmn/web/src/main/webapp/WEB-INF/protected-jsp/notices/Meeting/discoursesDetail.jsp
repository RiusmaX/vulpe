<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.mmn.notices.Meeting.main.discourses.speaker">
				<v:selectPopup property="speaker"
					identifier="id" description="name"
					action="/core/Member/select" popupId="speakerSelectPopup"
					popupProperties="speaker.id=id,speaker.name=name"
					size="40" popupWidth="420px"
					autocomplete="true"
					required="true" autocompleteValueList="${ever['membersAutocompleteValueList']}"
				/>
			</v:column>
			<v:column labelKey="label.mmn.notices.Meeting.main.discourses.assistant">
				<v:selectPopup property="assistant"
					identifier="id" description="name"
					action="/core/Member/select" popupId="speakerSelectPopup"
					popupProperties="speaker.id=id,speaker.name=name"
					size="40" popupWidth="420px"
					autocomplete="true"
					required="true" autocompleteValueList="${ever['membersAutocompleteValueList']}"
				/>
			</v:column>
			<v:column labelKey="label.mmn.notices.Meeting.main.discourses.topic">
				<v:text property="topic"
					size="40"
					maxlength="100"
					required="true"
				/>
			</v:column>
			<v:column labelKey="label.mmn.notices.Meeting.main.discourses.time">
				<v:text property="time"
					mask="I"
					required="true"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>
