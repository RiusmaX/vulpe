<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:textTranslate
	labelKey="label.portal.core.Menu.crud.name"
	property="name"
	size="40"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Menu.crud.description"
	property="description"
	size="100"
	required="true"
/>
<v:select
	labelKey="label.portal.core.Menu.crud.navigateToSection"
	property="navigateToSection.id"
	items="Section"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
/>
<v:text
	labelKey="label.portal.core.Menu.crud.url"
	property="url"
	size="100"
/>
<v:select
	labelKey="label.portal.core.Menu.crud.status"
	property="status" required="true"
/>
