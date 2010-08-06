<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:hidden property="id"/>
<table>
<tr>
<td>
<v:select
	labelKey="label.sitra.core.Objeto.crud.sistema"
	property="sistema.id"
	items="Sistema"
	itemKey="id"
	itemLabel="nome"
	showBlank="true" autoLoad="true"
	required="true"
/>
</td>
<td>
<v:select
	labelKey="label.sitra.core.Objeto.crud.origem"
	property="origem"
	showBlank="true" autoLoad="false"
/>
</td>
<td>
<v:select
	labelKey="label.sitra.core.Objeto.crud.destino"
	property="destino"
	showBlank="true" autoLoad="false"
/>
</td>
</tr>
<tr>
<td colspan="3">
<v:checkbox
	labelKey="label.sitra.core.Objeto.crud.compilarInvalidos"
	property="compilarInvalidos"
	fieldValue="true"
/>
</td>
</tr>
<tr>
<td colspan="3">
<v:text
	labelKey="label.sitra.core.Objeto.crud.descricao"
	property="descricao"
	size="100"
	required="true"
/>
</td>
</tr>
<tr>
<td colspan="3">
<v:textarea
	labelKey="label.sitra.core.Objeto.crud.justificativa"
	property="justificativa"
	cols="100" rows="3"
	required="true"
/>
</td>
</tr>
<tr>
<td colspan="3">
<v:text
	labelKey="label.sitra.core.Objeto.crud.demandas"
	property="demandas"
	size="100"
	required="true"
/>
</td>
</tr>
</table>