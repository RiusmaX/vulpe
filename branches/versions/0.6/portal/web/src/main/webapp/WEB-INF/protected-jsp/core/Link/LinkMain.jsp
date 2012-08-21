<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:hidden property="clicks"/>
<v:select
	labelKey="label.portal.core.Link.main.section"
	property="section.id"
	items="Section"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
/>
<v:select
	labelKey="label.portal.core.Link.main.category"
	property="category.id"
	items="Category"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Link.main.name"
	property="name"
	size="60"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Link.main.description"
	property="description"
	size="100"
	required="true"
/>
<v:text
	labelKey="label.portal.core.Link.main.url"
	property="url"
	size="100"
/>
<v:select
	labelKey="label.portal.core.Link.main.target"
	property="target"
	required="true"
/>
<v:select property="position" labelKey="label.portal.core.Link.main.positions" showBlank="true" />
<v:select
	labelKey="label.portal.core.Link.main.status"
	property="status"
	required="true"
/>
