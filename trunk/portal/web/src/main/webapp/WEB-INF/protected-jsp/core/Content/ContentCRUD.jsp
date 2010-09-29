<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:select
	labelKey="label.portal.core.Content.crud.section"
	property="section.id"
	items="Section"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
	required="true"
/>
<v:select
	labelKey="label.portal.core.Content.crud.category"
	property="category.id"
	items="Category"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Content.crud.title"
	property="title"
	size="40"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Content.crud.subtitle"
	property="subtitle"
	size="40"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Content.crud.miniText"
	property="miniText"
	size="100"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Content.crud.fullText"
	property="fullText"
	cols="80" rows="10"
	required="true"
/>
<v:checkbox
	labelKey="label.portal.core.Content.crud.escapeXml"
	property="escapeXml"
	fieldValue="true"
/>
