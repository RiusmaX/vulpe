<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:menu labelKey="label.transfere.menu.Index">
	<v:menu labelKey="label.transfere.menu.Index.start" action="/${vulpeCurrentLayout == 'BACKEND' ? 'backend' : 'frontend'}/Index" accesskey="I"/>
	<v:menu labelKey="label.transfere.menu.Index.sistema" action="/core/Sistema/select" accesskey="S"/>
	<v:menu labelKey="label.transfere.menu.Index.objeto">
		<v:menu labelKey="label.transfere.menu.Index.objeto.select" action="/core/Objeto/select" accesskey="O"/>
		<v:menu labelKey="label.transfere.menu.Index.objeto.transferir" action="/core/Objeto/create" accesskey="T"/>
		<v:menu labelKey="label.transfere.menu.Index.objeto.publicar" action="/core/ObjetoPublicacao/create" accesskey="P"/>
	</v:menu>
</v:menu>