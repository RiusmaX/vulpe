<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp"%>
<li><a id="menuVulpe" href="javascript:void(0);" class="current"
	onclick="vulpe.view.request.submitMenu('/Index/frontend/ajax.action');"
	title="<fmt:message key='label.site.menu.Index'/>"><span><fmt:message key='label.site.menu.Index'/></span></a></li>
<li><a id="menuLearn" href="javascript:void(0);"
	onclick="vulpe.view.request.submitMenu('/Learn/frontend/ajax.action');"
	title="<fmt:message key='label.site.menu.Learn'/>"><span><fmt:message key='label.site.menu.Learn'/></span></a></li>
<li><a id="menuCommunity" href="javascript:void(0);"
	onclick="vulpe.view.request.submitMenu('/Community/frontend/ajax.action');"
	title="<fmt:message key='label.site.menu.Community'/>"><span><fmt:message key='label.site.menu.Community'/></span></a></li>
<li><a id="menuCode" href="javascript:void(0);"
	onclick="vulpe.view.request.submitMenu('/Code/frontend/ajax.action');"
	title="<fmt:message key='label.site.menu.Code'/>"><span><fmt:message key='label.site.menu.Code'/></span></a></li>
<li><a id="menuModules" href="javascript:void(0);"
	onclick="vulpe.view.request.submitMenu('/Modules/frontend/ajax.action');"
	title="<fmt:message key='label.site.menu.Modules'/>"><span><fmt:message key='label.site.menu.Modules'/></span></a></li>