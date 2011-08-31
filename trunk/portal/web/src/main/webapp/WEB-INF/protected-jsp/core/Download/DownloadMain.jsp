<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:hidden property="downloads"/>
<v:textTranslate
	labelKey="label.portal.core.Download.main.name"
	property="name"
	size="60"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Download.main.description"
	property="description"
	size="100"
	required="true"
/>
<v:text
	labelKey="label.portal.core.Download.main.url"
	property="url"
	size="100" required="true"
/>
<v:select
	labelKey="label.portal.core.Download.main.status"
	property="status"
	required="true"
/>
