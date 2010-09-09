<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:selectPopup labelKey="label.gmn.ministerio.Relatorio.select.publicador" property="publicador"
	identifier="id" description="nome" action="/core/Publicador/select" popupId="publicadorSelectPopup"
	popupProperties="publicador.id=id,publicador.nome=nome" size="40" popupWidth="420px"
	autocomplete="true" autocompleteMinLength="1" />
<v:select labelKey="label.gmn.ministerio.Relatorio.select.mes" property="mes" showBlank="true"
	autoLoad="false" required="true" />