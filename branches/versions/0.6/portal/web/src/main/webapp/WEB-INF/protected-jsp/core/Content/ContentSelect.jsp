<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:select
	labelKey="label.portal.core.Content.select.section"
	property="section.id"
	items="Section"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
/>
<%--
<v:textTranslate
	labelKey="label.portal.core.Content.select.title"
	property="title"
	size="40"
	required="true"
/>
--%>