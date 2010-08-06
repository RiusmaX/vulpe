<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<li><a href="javascript:void(0);" class="current"
	title="<fmt:message key='label.sitra.menu.Index'/>"><span><fmt:message
	key='label.sitra.menu.Index' /></span></a>
<ul>
	<li><a href="javascript:void(0);" class="current"
		onclick="vulpe.view.request.submitMenu('/frontend/Index/ajax');"
		title="<fmt:message key='label.sitra.menu.Index.start'/>"><span><fmt:message
		key='label.sitra.menu.Index.start' /></span></a></li>
	<li><a href="javascript:void(0);" class="current"
		onclick="vulpe.view.request.submitMenu('/core/Sistema/select/ajax');"
		title="<fmt:message key='label.sitra.menu.Index.sistema'/>"><span><fmt:message
		key='label.sitra.menu.Index.sistema' /></span></a></li>
</ul>
</li>