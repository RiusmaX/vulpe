<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<li><a href="javascript:void(0);" class="current"
	title="<fmt:message key='label.sirci.menu.Index'/>"><span><fmt:message
	key='label.sirci.menu.Index' /></span></a>
<ul>
	<li><a href="javascript:void(0);" class="current"
		onclick="vulpe.view.request.submitMenu('/core/TipoApontamento/select/ajax');"
		title="<fmt:message key='label.sirci.menu.core.TipoApontamento.select'/>"><span><fmt:message
		key='label.sirci.menu.core.TipoApontamento.select' /></span></a></li>
	<li><a href="javascript:void(0);" class="current"
		onclick="vulpe.view.request.submitMenu('/core/TipoApontamento/twice/ajax');"
		title="<fmt:message key='label.sirci.menu.core.TipoApontamento.twice'/>"><span><fmt:message
		key='label.sirci.menu.core.TipoApontamento.twice' /></span></a></li>
	<li><a href="javascript:void(0);" class="current"
		onclick="vulpe.view.request.submitMenu('/core/TipoApontamento/tabular/ajax');"
		title="<fmt:message key='label.sirci.menu.core.TipoApontamento.tabular'/>"><span><fmt:message
		key='label.sirci.menu.core.TipoApontamento.tabular' /></span></a></li>
	<li><a href="javascript:void(0);" class="current"
		onclick="vulpe.view.request.submitMenu('/core/Apontamento/select/ajax');"
		title="<fmt:message key='label.sirci.menu.core.Apontamento.select'/>"><span><fmt:message
		key='label.sirci.menu.core.Apontamento.select' /></span></a></li>
	<li><a href="javascript:void(0);" class="current"
		onclick="vulpe.view.request.submitMenu('/core/Apontamento/twice/ajax');"
		title="<fmt:message key='label.sirci.menu.core.Apontamento.twice'/>"><span><fmt:message
		key='label.sirci.menu.core.Apontamento.twice' /></span></a></li>
</ul>
</li>