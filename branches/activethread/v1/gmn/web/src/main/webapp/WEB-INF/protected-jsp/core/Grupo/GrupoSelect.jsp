<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:select
	labelKey="label.gmn.core.Grupo.select.congregacao"
	property="congregacao.id"
	items="Congregacao"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="true"
	required="true"
/>
