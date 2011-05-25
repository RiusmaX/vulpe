<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:select
	labelKey="label.mmn.core.Group.select.congregation"
	property="congregation.id"
	items="Congregation"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
	required="true" value="${congregationSelecionada.id}" showAsText="true"
/>
