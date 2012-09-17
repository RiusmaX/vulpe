<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:hidden property="views"/>
<div class="line">
<v:select
	labelKey="label.portal.core.Content.main.section"
	property="section.id"
	items="Section"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
	required="true"
/>
<v:select
	labelKey="label.portal.core.Content.main.category"
	property="category.id"
	items="Category"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
	required="true"
/>
</div>
<fmt:message key="label.portal.core.Content.main.addRemoveVideo.message" var="message"/>
<v:action javascript="app.addRemoveVideo('${message}')" labelKey="label.portal.core.Content.main.addRemoveVideo" showIconOfButton="false"/>
<fieldset id="video" style="${empty entity.videoURL ? 'display:none' : ''}">
<legend><fmt:message key="label.portal.core.Content.main.addVideo"/></legend>
<div class="line">
<v:text
	labelKey="label.portal.core.Content.main.videoURL"
	property="videoURL"
	size="80"
/>
<v:text
	labelKey="label.portal.core.Content.main.videoWidth"
	property="videoWidth"
	size="5"
	mask="INTEGER"
/>
<v:text
	labelKey="label.portal.core.Content.main.videoHeight"
	property="videoHeight"
	size="5"
	mask="INTEGER"
/>
</div>
</fieldset>
<v:textTranslate
	labelKey="label.portal.core.Content.main.title"
	property="title"
	size="100"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Content.main.subtitle"
	property="subtitle"
	size="100"
/>
<v:textTranslate
	labelKey="label.portal.core.Content.main.miniText"
	property="miniText"
	size="100"
/>
<v:textTranslate
	labelKey="label.portal.core.Content.main.fullText"
	property="fullText"
	cols="120" rows="15"
	required="false"
	editor="true"
/>
<v:checkbox
	labelKey="label.portal.core.Content.main.escapeXml"
	property="escapeXml"
	fieldValue="true"
/>
<v:select
	labelKey="label.portal.core.Content.main.status"
	property="status"
	required="true"
/>
