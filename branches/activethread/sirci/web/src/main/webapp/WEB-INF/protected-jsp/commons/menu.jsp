<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:menu labelKey="label.sirci.menu.Index">
	<v:menu labelKey="label.sirci.menu.core.TipoApontamento.select" action="/core/TipoApontamento/select"/>
	<v:menu labelKey="label.sirci.menu.core.TipoApontamento.tabular" action="/core/TipoApontamento/tabular"/>
	<v:menu labelKey="label.sirci.menu.core.TipoApontamento.twice" action="/core/TipoApontamento/twice"/>
	<v:menu labelKey="label.sirci.menu.core.Apontamento.select" action="/core/Apontamento/select"/>
	<v:menu labelKey="label.sirci.menu.core.Apontamento.twice" action="/core/Apontamento/twice"/>
</v:menu>