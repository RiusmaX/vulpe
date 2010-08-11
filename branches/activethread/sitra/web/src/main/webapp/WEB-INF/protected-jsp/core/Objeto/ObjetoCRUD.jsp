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
	property="origem" items="${now['origem']}"
	showBlank="true" autoLoad="false" required="true"
/>
</td>
<td>
<v:select
	labelKey="label.sitra.core.Objeto.crud.destino"
	property="destino" items="${now['destino']}"
	showBlank="true" autoLoad="false" required="true"
/>
</td>
</tr>
<c:if test="${empty now['publicacao']}">
<tr>
<td colspan="3">
<v:checkbox
	labelKey="label.sitra.core.Objeto.crud.compilarInvalidos"
	property="compilarInvalidos"
	fieldValue="true"
/>
</td>
</tr>
</c:if>
<c:if test="${now['publicacao']}">
<tr>
<td colspan="3">
<v:text
	labelKey="label.sitra.core.Objeto.crud.descricao"
	property="descricao"
	size="100" upperCase="true"
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
</c:if>
</table>