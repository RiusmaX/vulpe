<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<p>
<v:select
	labelKey="label.gmn.core.Congregacao.crud.nome"
	property="congregacao.id"
	items="${congregacoesUsuario}"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="false"
	required="true" paragraph="false"
/>
<v:action labelKey="label.gmn.selecionar" helpKey="help.gmn.selecionar" elementId="Selecionar" action="selecionarValidate" showButtonAsImage="false" showButtonText="true" />
</p>