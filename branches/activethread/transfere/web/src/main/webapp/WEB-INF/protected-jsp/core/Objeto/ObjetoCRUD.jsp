<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<table>
<tr>
<td>
<v:hidden property="sistema.id" value="${sistemaSelecionado.id}"/>
<p>
<v:label
	key="label.transfere.core.Objeto.crud.sistema" breakLine="true"
/>
${sistemaSelecionado.nome}
</p>
</td>
<td>
<v:select
	labelKey="label.transfere.core.Objeto.crud.origem"
	property="origem" items="${now['origem']}"
	showBlank="true" autoLoad="false" required="true"
/>
</td>
<td>
<v:select
	labelKey="label.transfere.core.Objeto.crud.destino"
	property="destino" items="${now['destino']}"
	showBlank="true" autoLoad="false" required="true"
/>
</td>
</tr>
<c:if test="${empty now['publicacao']}">
<tr>
<td colspan="3">
<v:checkbox
	labelKey="label.transfere.core.Objeto.crud.compilarInvalidos"
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
	labelKey="label.transfere.core.Objeto.crud.descricao"
	property="descricao"
	size="100" upperCase="true"
	required="true"
/>
</td>
</tr>
<tr>
<td colspan="3">
<v:textarea
	labelKey="label.transfere.core.Objeto.crud.justificativa"
	property="justificativa"
	cols="100" rows="3"
	required="true"
/>
</td>
</tr>
<tr>
<td colspan="3">
<v:text
	labelKey="label.transfere.core.Objeto.crud.demandas"
	property="demandas"
	size="100"
	required="true"
/>
</td>
</tr>
</c:if>
</table>