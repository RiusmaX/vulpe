<li><a href="javascript:void(0);" title="M�dulo Principal">Principal</a>
<ul>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/core/Congregacao/select/prepare/ajax');"
		title="Gerenciamento de Congrega��o">Congrega��es</a></li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/core/Grupo/select/prepare/ajax');"
		title="Gerenciamento de Grupo">Grupos</a></li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/core/Publicador/select/prepare/ajax');"
		title="Gerenciamento de Publicadores">Publicadores</a>
		</li>
</ul>
</li>
<li><a href="javascript:void(0);" title="M�dulo Publica��es">Publica��es</a>
<ul>
<sec:authorize ifAllGranted="ROLE_ADMINISTRADOR">
</sec:authorize>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/publicacoes/TipoPublicacao/tabular/prepare/ajax');"
		title="Gerenciamento de Tipos de Publica��es">Tipos de Publica��es</a></li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/publicacoes/Publicacao/select/prepare/ajax');"
		title="Gerenciamento de Publica��es">Publica��es</a></li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/publicacoes/Pedido/select/prepare/ajax');"
		title="Gerenciamento de Pedidos">Pedidos</a></li>
</ul>
</li>
<li><a href="javascript:void(0);" title="M�dulo Minist�rio">Minist�rio</a>
<ul>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/ministerio/Relatorio/select/prepare/ajax');"
		title="Gerenciamento de Relatorio">Relat�rio</a></li>
</ul>
</li>
<li><a href="javascript:void(0);" title="M�dulo An�ncios">An�ncios</a>
<ul>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/anuncios/EscolaMinisterio/select/prepare/ajax');"
		title="Gerenciamento de Escola do Minist�rio">Escola do Minist�rio</a></li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/anuncios/ReuniaoServico/select/prepare/ajax');"
		title="Gerenciamento de Reuni�o de Servi�o">Reuni�o de Servi�o</a></li>
</ul>
</li>