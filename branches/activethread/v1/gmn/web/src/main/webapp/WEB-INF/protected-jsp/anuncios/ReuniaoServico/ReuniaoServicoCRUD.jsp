<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:hidden property="id"/>
<v:selectPopup
	labelKey="label.gmn.anuncios.ReuniaoServico.crud.presidente"
	property="presidente"
	identifier="id" description="nome"
	action="/core/Publicador/select/prepare" popupId="presidenteSelectPopup"
	popupProperties="presidente.id=id,presidente.nome=nome"
	size="40" popupWidth="420px"
	autoComplete="true"
	required="true"
/>
<v:date
	labelKey="label.gmn.anuncios.ReuniaoServico.crud.data"
	property="data"
	required="true"
/>
