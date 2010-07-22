<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:hidden property="id"/>
<v:selectPopup
	labelKey="label.gmn.anuncios.Leitor.crud.publicador"
	property="publicador"
	identifier="id" description="nome"
	action="/core/Publicador/select" popupId="publicadorSelectPopup"
	popupProperties="publicador.id=id,publicador.nome=nome"
	size="40" popupWidth="420px"
	autoComplete="true"
	required="true"
/>
<v:date
	labelKey="label.gmn.anuncios.Leitor.crud.data"
	property="data"
	required="true"
	validateType="DATE"
	validateDatePattern="dd/MM/yyyy"
/>
<v:select
	labelKey="label.gmn.anuncios.Leitor.crud.tipoLeitura"
	property="tipoLeitura"
	showBlank="true" autoLoad="false"
	required="true"
/>
