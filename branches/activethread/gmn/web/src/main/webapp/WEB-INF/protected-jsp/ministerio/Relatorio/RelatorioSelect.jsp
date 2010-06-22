<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:selectPopup
	labelKey="label.gmn.ministerio.Relatorio.select.pulicador"
	property="pulicador"
	identifier="id" description="nome"
	action="/core/Publicador/select/prepare" popupId="pulicadorSelectPopup"
	popupProperties="pulicador.id=id,pulicador.nome=nome"
	size="40" popupWidth="420px"
	autoComplete="true"
/>
