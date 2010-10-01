<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:select
	labelKey="label.portal.core.Link.crud.section"
	property="section.id"
	items="Section"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
/>
<v:select
	labelKey="label.portal.core.Link.crud.category"
	property="category.id"
	items="Category"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Link.crud.name"
	property="name"
	size="60"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Link.crud.description"
	property="description"
	size="100"
	required="true"
/>
<v:text
	labelKey="label.portal.core.Link.crud.url"
	property="url"
	size="100"
	required="true"
/>
<v:select
	labelKey="label.portal.core.Link.crud.target"
	property="target"
	required="true"
/>
<fieldset id="positions">
	<legend><v:label key="label.portal.core.Link.crud.positions" /></legend>
	<v:checkboxlist property="positions" enumeration="Position" />
</fieldset>
<v:select
	labelKey="label.portal.core.Link.crud.status"
	property="status"
	required="true"
/>
