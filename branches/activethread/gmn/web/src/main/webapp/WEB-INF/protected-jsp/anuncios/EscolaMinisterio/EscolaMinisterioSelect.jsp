<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:selectPopup
	labelKey="label.gmn.anuncios.EscolaMinisterio.select.presidente"
	property="presidente"
	identifier="id" description="nome"
	action="/core/Publicador/select/prepare" popupId="presidenteSelectPopup"
	popupProperties="presidente.id=id,presidente.nome=nome"
	size="40" popupWidth="420px"
	autoComplete="true"
	required="true"
/>
