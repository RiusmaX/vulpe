<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
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
<fieldset>
<table>
<tr>
<td colspan="2">
<v:text
	labelKey="label.portal.core.Content.main.videoURL"
	property="videoURL"
	size="40"
/>
</td>
</tr>
<tr>
<td>
<v:text
	labelKey="label.portal.core.Content.main.videoWidth"
	property="videoWidth"
	size="5"
	mask="INTEGER"
/>
</td>
<td>
<v:text
	labelKey="label.portal.core.Content.main.videoHeight"
	property="videoHeight"
	size="5"
	mask="INTEGER"
/>
</td>
</tr>
</table>
</fieldset>
<v:textTranslate
	labelKey="label.portal.core.Content.main.title"
	property="title"
	size="40"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Content.main.subtitle"
	property="subtitle"
	size="40"
	required="true"
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
