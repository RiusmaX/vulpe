<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:textTranslate
	labelKey="label.portal.core.Community.crud.name"
	property="name"
	size="40"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Community.crud.description"
	property="description"
	size="100"
	required="true"
/>
<v:text
	labelKey="label.portal.core.Community.crud.url"
	property="url"
	size="100"
/>
<v:select
	labelKey="label.portal.core.Community.crud.target"
	property="target"
	showBlank="true" autoLoad="false"
	required="true"
/>
<v:select
	labelKey="label.portal.core.Community.crud.status"
	property="status"
	required="true"
/>
