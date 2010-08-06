<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<table>
	<tr>
		<td><v:select labelKey="label.sitra.core.Objeto.select.tipoObjeto" property="tipoObjeto"
			showBlank="true" autoLoad="false" /></td>
		<td></td>
	</tr>
	<tr>
		<td>
		<p><v:label key="label.sitra.core.Objeto.select.periodo" /><br>
		<v:date property="dataInicial" paragraph="false" />até<v:date property="dataFinal"
			paragraph="false" /></p>
		</td>
		<td><v:select labelKey="label.sitra.core.Objeto.select.destino" property="destino"
			showBlank="true" autoLoad="false" /></td>
	</tr>
</table>