<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:select
	labelKey="label.portal.core.Social.main.network"
	property="network"
	showBlank="true" autoLoad="false"
	required="true"
/>
<v:text
	labelKey="label.portal.core.Social.main.name"
	property="name"
	size="30"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Social.main.description"
	property="description"
	size="60"
/>
<v:select
	labelKey="label.portal.core.Social.main.target"
	property="target"
	showBlank="true" autoLoad="false"
	required="true"
/>
<v:select
	labelKey="label.portal.core.Social.main.status"
	property="status"
	required="true"
/>