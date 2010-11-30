<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:textTranslate
	labelKey="label.portal.core.Menu.main.name"
	property="name"
	size="40"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Menu.main.description"
	property="description"
	size="100"
	required="true"
/>
<v:select
	labelKey="label.portal.core.Menu.main.navigateToSection"
	property="navigateToSection.id"
	items="Section"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
/>
<v:text
	labelKey="label.portal.core.Menu.main.url"
	property="url"
	size="100"
/>
<v:checkbox
	labelKey="label.portal.core.Menu.main.selectOnLoad"
	property="selectOnLoad"
	fieldValue="true"
/>
<v:select
	labelKey="label.portal.core.Menu.main.status"
	property="status" required="true"
/>
