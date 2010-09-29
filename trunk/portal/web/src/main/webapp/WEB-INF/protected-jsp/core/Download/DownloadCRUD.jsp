<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<v:textTranslate
	labelKey="label.portal.core.Download.crud.name"
	property="name"
	size="60"
	required="true"
/>
<v:textTranslate
	labelKey="label.portal.core.Download.crud.description"
	property="description"
	size="100"
	required="true"
/>
<v:text
	labelKey="label.portal.core.Download.crud.url"
	property="url"
	size="100" required="true"
/>
