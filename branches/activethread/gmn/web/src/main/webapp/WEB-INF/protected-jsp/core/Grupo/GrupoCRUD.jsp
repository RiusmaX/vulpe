<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:hidden property="id"/>
<v:text
	labelKey="label.gmn.core.Grupo.crud.nome"
	property="nome"
	size="40"
	maxlength="40"
	validateType="STRING"
	validateMinLength="5"
	validateMaxLength="40"
	showAsText="true"
/>
<v:select
	labelKey="label.gmn.core.Grupo.crud.congregacao"
	property="congregacao.id"
	items="Congregacao"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="true"
	required="true"
	showAsText="true"
/>
