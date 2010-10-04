<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:menu elementId="Principal" labelKey="label.transfere.menu.Index">
	<c:choose>
		<c:when test="${vulpeCurrentLayout == 'BACKEND'}">
			<v:menu elementId="Principal" labelKey="label.transfere.menu.Index.start" action="/backend/Index" accesskey="I"/>
		</c:when>
		<c:otherwise>
			<v:menu elementId="Principal" labelKey="label.transfere.menu.Index.start" action="/frontend/Index" accesskey="I"/>
		</c:otherwise>
	</c:choose>
	<v:menu elementId="Sistema" labelKey="label.transfere.menu.Index.sistema" action="/core/Sistema/select" accesskey="S"/>
	<v:menu elementId="Objeto" labelKey="label.transfere.menu.Index.objeto">
		<v:menu elementId="PerquisarObjeto" labelKey="label.transfere.menu.Index.objeto.select" action="/core/Objeto/select" accesskey="O"/>
		<v:menu elementId="TransferirObjeto" labelKey="label.transfere.menu.Index.objeto.transferir" action="/core/Objeto/create" accesskey="T"/>
		<v:menu elementId="PublicarObjeto" labelKey="label.transfere.menu.Index.objeto.publicar" action="/core/ObjetoPublicacao/create" accesskey="P"/>
	</v:menu>
	<v:menu elementId="Agenda" labelKey="label.transfere.menu.Index.agenda" action="/core/Agenda/select" accesskey="A"/>
</v:menu>
<c:if test="${vulpeCurrentLayout == 'FRONTEND'}">
<v:menu elementId="Security" labelKey="label.vulpe.menu.Security" roles="ADMINISTRADOR">
	<v:menu elementId="Role" labelKey="label.vulpe.menu.Security.Role" action="/security/Role/tabular"/>
	<v:menu elementId="User" labelKey="label.vulpe.menu.Security.User" action="/security/User/select"/>
	<v:menu elementId="SecureResource" labelKey="label.vulpe.menu.Security.Resource" action="/security/SecureResource/select" />
</v:menu>
</c:if>