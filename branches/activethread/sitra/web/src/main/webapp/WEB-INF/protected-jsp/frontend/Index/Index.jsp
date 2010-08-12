<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<p>
<v:select
	labelKey="label.sitra.core.Objeto.crud.sistema"
	property="sistema.id"
	items="${now['sistemas']}"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="false"
	required="true" paragraph="false"
/>
<v:action labelKey="label.sitra.selecionar" helpKey="help.sitra.selecionar" elementId="Selecionar" action="selecionarValidate" showButtonAsImage="false" showButtonText="true" />
</p>