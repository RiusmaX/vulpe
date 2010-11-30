<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:textTranslate
	labelKey="label.portal.core.Community.main.name"
	property="name"
	size="40"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Community.main.description"
	property="description"
	size="100"
	required="true"
/>
<v:text
	labelKey="label.portal.core.Community.main.url"
	property="url"
	size="100"
/>
<v:select
	labelKey="label.portal.core.Community.main.target"
	property="target"
	showBlank="true" autoLoad="false"
	required="true"
/>
<v:select
	labelKey="label.portal.core.Community.main.status"
	property="status"
	required="true"
/>
