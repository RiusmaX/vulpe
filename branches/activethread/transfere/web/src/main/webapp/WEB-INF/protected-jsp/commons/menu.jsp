<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:menu labelKey="label.transfere.menu.Index">
	<c:choose>
		<c:when test="${vulpeCurrentLayout == 'BACKEND'}">
			<v:menu labelKey="label.transfere.menu.Index.start" action="/backend/Index" accesskey="I"/>
		</c:when>
		<c:otherwise>
			<v:menu labelKey="label.transfere.menu.Index.start" action="/frontend/Index" accesskey="I"/>
		</c:otherwise>
	</c:choose>
	<v:menu labelKey="label.transfere.menu.Index.sistema" action="/core/Sistema/select" accesskey="S"/>
	<v:menu labelKey="label.transfere.menu.Index.objeto">
		<v:menu labelKey="label.transfere.menu.Index.objeto.select" action="/core/Objeto/select" accesskey="O"/>
		<v:menu labelKey="label.transfere.menu.Index.objeto.transferir" action="/core/Objeto/create" accesskey="T"/>
		<v:menu labelKey="label.transfere.menu.Index.objeto.publicar" action="/core/ObjetoPublicacao/create" accesskey="P"/>
	</v:menu>
</v:menu>