<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<p>
<v:select
	labelKey="label.transfere.core.Objeto.crud.sistema"
	property="sistema.id"
	items="${sistemasUsuario}"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="false"
	required="true" paragraph="false"
/>
<v:action labelKey="label.transfere.selecionar" helpKey="help.transfere.selecionar" elementId="Selecionar" action="selecionarValidate" showButtonAsImage="false" showButtonText="true" />
</p>