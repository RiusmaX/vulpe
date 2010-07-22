<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:select
	labelKey="label.sirci.core.Apontamento.crud.documentoOrigem"
	property="documentoOrigem.id"
	items="${listaDocumentoOrigem}"
	itemKey="id" size="3"
	itemLabel="descricao"
	required="true"
/>