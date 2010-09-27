<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:textTranslate
	labelKey="label.portal.core.Portal.crud.name"
	property="name"
	size="40"
	maxlength="100"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Portal.crud.title"
	property="title"
	size="40"
	maxlength="100"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Portal.crud.description"
	property="description"
	rows="3" cols="50"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Portal.crud.copyright"
	property="copyright"
	size="40"
	maxlength="100"
	required="true"
/>
<v:checkbox
	labelKey="label.portal.core.Portal.crud.offline"
	property="offline"
	fieldValue="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Portal.crud.offlineMessage"
	property="offlineMessage"
	size="40"
	maxlength="100"
	required="true"
/>
<v:select
	labelKey="label.portal.core.Portal.crud.status"
	property="status"
	showBlank="true" autoLoad="false"
	required="true"
/>
