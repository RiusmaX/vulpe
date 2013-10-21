<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<script type="text/javascript">
	$(document).ready(function() {
		app.arrays.categories = ${now['jsonCachedClasses']['Category']};
		vulpe.util.getElementField("category.id").trigger("change");
	});
</script>
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
	onchange="app.showByCategory($(this).val())"
/>
</div>
<div id="contentType" style="display: none" class="type">
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
</div>
<div id="downloadLinkType" style="display: none" class="type">
<v:text
	labelKey="label.portal.core.Content.main.url"
	property="url"
	size="100"
/>
<v:select
	labelKey="label.portal.core.Content.main.target"
	property="target"
	required="true"
/>
<v:select property="position" labelKey="label.portal.core.Content.main.positions" showBlank="true" />
</div>
<v:select
	labelKey="label.portal.core.Content.main.status"
	property="status"
	required="true"
/>
