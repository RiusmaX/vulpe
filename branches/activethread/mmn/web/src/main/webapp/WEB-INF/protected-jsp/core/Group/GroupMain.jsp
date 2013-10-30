<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:hidden property="id"/>
<v:text
	labelKey="label.mmn.core.Group.main.name"
	property="name"
	size="40"
	maxlength="40"
	validateType="STRING"
	validateMinLength="5"
	validateMaxLength="40"
	showAsText="true"
/>
<v:select
	labelKey="label.mmn.core.Group.main.congregation"
	property="congregation.id"
	items="Congregation"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="true"
	required="true"
	showAsText="true"
/>
