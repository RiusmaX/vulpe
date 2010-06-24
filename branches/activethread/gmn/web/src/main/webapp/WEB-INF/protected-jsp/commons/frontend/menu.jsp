<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<li><a href="javascript:void(0);" title="Módulo Principal">Principal</a>
<ul>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/core/Congregacao/select/ajax');"
		title="Gerenciamento de Congregação">Congregações</a></li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/core/Grupo/select/ajax');"
		title="Gerenciamento de Grupo">Grupos</a></li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/core/Publicador/select/ajax');"
		title="Gerenciamento de Publicadores">Publicadores</a>
		</li>
</ul>
</li>
<li><a href="javascript:void(0);" title="Módulo Publicações">Publicações</a>
<ul>
<sec:authorize ifAllGranted="ROLE_ADMINISTRADOR">
</sec:authorize>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/publicacoes/TipoPublicacao/tabular/ajax');"
		title="Gerenciamento de Tipos de Publicações">Tipos de Publicações</a></li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/publicacoes/Publicacao/select/ajax');"
		title="Gerenciamento de Publicações">Publicações</a></li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/publicacoes/Pedido/select/ajax');"
		title="Gerenciamento de Pedidos">Pedidos</a></li>
</ul>
</li>
<li><a href="javascript:void(0);" title="Módulo Ministério">Ministério</a>
<ul>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/ministerio/Relatorio/select/ajax');"
		title="Gerenciamento de Relatorio">Relatório</a></li>
</ul>
</li>
<li><a href="javascript:void(0);" title="Módulo Anúncios">Anúncios</a>
<ul>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/anuncios/EscolaMinisterio/select/ajax');"
		title="Gerenciamento de Escola do Ministério">Escola do Ministério</a></li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/anuncios/ReuniaoServico/select/ajax');"
		title="Gerenciamento de Reunião de Serviço">Reunião de Serviço</a></li>
</ul>
</li>