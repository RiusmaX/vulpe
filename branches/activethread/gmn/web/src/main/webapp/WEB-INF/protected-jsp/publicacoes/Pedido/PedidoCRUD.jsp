<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>

<v:hidden property="id"/>
<v:selectPopup
	labelKey="label.gmn.publicacoes.Pedido.crud.publicador"
	property="publicador"
	identifier="id" description="nome"
	action="/core/Publicador/select/prepare" popupId="publicadorSelectPopup"
	popupProperties="publicador.id=id,publicador.nome=nome"
	size="40" popupWidth="420px" required="true" autoComplete="true"
/>
<v:date
	labelKey="label.gmn.publicacoes.Pedido.crud.data"
	property="data" required="true"
/>
<v:checkbox
	labelKey="label.gmn.publicacoes.Pedido.crud.entregue"
	property="entregue"
	fieldValue="true"
/>
