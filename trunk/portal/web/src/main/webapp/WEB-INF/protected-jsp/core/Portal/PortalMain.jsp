<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:textTranslate
	labelKey="label.portal.core.Portal.main.name"
	property="name"
	size="40"
	maxlength="100"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Portal.main.title"
	property="title"
	size="40"
	maxlength="100"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Portal.main.description"
	property="description"
	rows="3" cols="50"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Portal.main.copyright"
	property="copyright"
	size="40"
	maxlength="100"
	required="true"
/>
<v:checkbox
	labelKey="label.portal.core.Portal.main.offline"
	property="offline"
	fieldValue="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Portal.main.offlineMessage"
	property="offlineMessage"
	size="40"
	maxlength="100"
	required="true"
/>
<v:select
	labelKey="label.portal.core.Portal.main.status"
	property="status"
	showBlank="true" autoLoad="false"
	required="true"
/>
