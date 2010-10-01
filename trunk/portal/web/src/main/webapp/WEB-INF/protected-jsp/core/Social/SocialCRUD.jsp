<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:select
	labelKey="label.portal.core.Social.crud.network"
	property="network"
	showBlank="true" autoLoad="false"
	required="true"
/>
<v:text
	labelKey="label.portal.core.Social.crud.name"
	property="name"
	size="30"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Social.crud.description"
	property="description"
	size="60"
/>
<v:select
	labelKey="label.portal.core.Social.crud.target"
	property="target"
	showBlank="true" autoLoad="false"
	required="true"
/>
<v:select
	labelKey="label.portal.core.Social.crud.status"
	property="status"
	required="true"
/>