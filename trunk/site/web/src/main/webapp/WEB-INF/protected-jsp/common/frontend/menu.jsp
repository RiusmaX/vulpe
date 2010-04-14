<%@include file="/WEB-INF/protected-jsp/common/common.jsp"%>
<div id="menuleft"></div>
<li><a href="javascript:void(0);" class="current"
	onclick="vulpe.view.request.submitMenu('/publicacoes/TipoPublicacao/tabular/prepare/ajax.action');"
	title="Vulpe"><span>Vulpe</span></a></li>
<li><a href="javascript:void(0);"
	onclick="vulpe.view.request.submitMenu('/publicacoes/Publicacao/select/prepare/ajax.action');"
	title="Cadastro de Publicações"><span>Publicações</span></a></li>
<li><a href="javascript:void(0);"
	onclick="vulpe.view.request.submitMenu('/publicacoes/Publicador/select/prepare/ajax.action');"
	title="Cadastro de Publicadores"><span>Publicadores</span></a></li>
<li><a href="javascript:void(0);"
	onclick="vulpe.view.request.submitMenu('/publicacoes/Pedido/select/prepare/ajax.action');"
	title="Cadastro de Pedidos"><span>Pedidos</span></a></li>
<div id="menuright"></div>