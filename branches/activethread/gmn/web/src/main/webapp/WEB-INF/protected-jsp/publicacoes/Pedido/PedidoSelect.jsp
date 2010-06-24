<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>

<v:selectPopup
	labelKey="label.gmn.publicacoes.Pedido.select.publicador"
	property="publicador"
	identifier="id" description="nome"
	action="/core/Publicador/select" popupId="publicadorSelectPopup"
	popupProperties="publicador.id=id,publicador.nome=nome"
	size="40" popupWidth="420px" autoComplete="true"
/>
<v:checkbox
	labelKey="label.gmn.publicacoes.Pedido.select.entregue"
	property="entregue"
	fieldValue="true"
/>
