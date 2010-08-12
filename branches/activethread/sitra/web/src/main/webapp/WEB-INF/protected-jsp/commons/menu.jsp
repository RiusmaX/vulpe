<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<v:menu labelKey="label.sitra.menu.Index">
	<v:menu labelKey="label.sitra.menu.Index.start" action="/backend/Index" accesskey="I"/>
	<v:menu labelKey="label.sitra.menu.Index.sistema" action="/core/Sistema/select" accesskey="S"/>
	<v:menu labelKey="label.sitra.menu.Index.objeto" action="/core/Objeto/select" accesskey="O"/>
</v:menu>