<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:text
	labelKey="label.gmn.core.Publicador.select.nome"
	property="nome"
	size="50"
	maxlength="60"
	required="true"
/>
<v:select
	labelKey="label.gmn.core.Publicador.select.grupo"
	property="grupo.id"
	items="Grupo"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="true"
/>
<%--
<v:checkbox
	labelKey="label.gmn.core.Publicador.select.indicador"
	property="indicador"
	fieldValue="true"
/>
<v:checkbox
	labelKey="label.gmn.core.Publicador.select.leitor"
	property="leitor"
	fieldValue="true"
/>
<v:checkbox
	labelKey="label.gmn.core.Publicador.select.microfone"
	property="microfone"
	fieldValue="true"
/>
 --%>