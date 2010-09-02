<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:menu labelKey="label.gmn.menu.Index">
	<v:menu labelKey="label.gmn.menu.Index.congregacoes" action="/core/Congregacao/select"/>
	<%--<v:menu labelKey="label.gmn.menu.Index.grupos" action="/core/Grupo/select"/> --%>
	<v:menu labelKey="label.gmn.menu.Index.publicadores" action="/core/Publicador/select"/>
</v:menu>
<v:menu labelKey="label.gmn.menu.Publicacoes">
	<v:menu labelKey="label.gmn.menu.Publicacoes.tiposPublicacoes" action="/publicacoes/TipoPublicacao/tabular"/>
	<v:menu labelKey="label.gmn.menu.Publicacoes.publicacoes" action="/publicacoes/Publicacao/select"/>
	<v:menu labelKey="label.gmn.menu.Publicacoes.pedidos" action="/publicacoes/Pedido/select"/>
</v:menu>
<v:menu labelKey="label.gmn.menu.Ministerio">
	<v:menu labelKey="label.gmn.menu.Ministerio.relatorio" action="/ministerio/Relatorio/select"/>
</v:menu>
<v:menu labelKey="label.gmn.menu.Anuncios">
	<v:menu labelKey="label.gmn.menu.Anuncios.escolaMinisterio" action="/anuncios/Reuniao/select"/>
	<v:menu labelKey="label.gmn.menu.Anuncios.reuniaoServico" action="/anuncios/Reuniao/select"/>
</v:menu>
<sec:authorize ifAllGranted="ROLE_ADMINISTRADOR">
</sec:authorize>