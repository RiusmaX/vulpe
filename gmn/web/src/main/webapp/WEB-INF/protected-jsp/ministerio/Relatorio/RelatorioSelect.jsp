<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:selectPopup
	labelKey="label.gmn.ministerio.Relatorio.select.pulicador"
	property="pulicador"
	identifier="id" description="nome"
	action="/core/Publicador/select" popupId="pulicadorSelectPopup"
	popupProperties="pulicador.id=id,pulicador.nome=nome"
	size="40" popupWidth="420px"
	autoComplete="true"
/>
<p><v:label key="label.gmn.ministerio.Relatorio.select.periodo" /><br>
<v:date property="dataInicial" paragraph="false" />at�<v:date property="dataFinal"
	paragraph="false" /></p>