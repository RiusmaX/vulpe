<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<li><a id="menuVulpe" href="javascript:void(0);" class="current"
	onclick="vulpe.view.request.submitMenu('/frontend/Index/ajax');"
	title="<fmt:message key='label.site.menu.Index'/>"><span><fmt:message key='label.site.menu.Index'/></span></a></li>
<li><a id="menuLearn" href="javascript:void(0);"
	onclick="vulpe.view.request.submitMenu('/frontend/Learn/ajax');"
	title="<fmt:message key='label.site.menu.Learn'/>"><span><fmt:message key='label.site.menu.Learn'/></span></a></li>
<li><a id="menuCommunity" href="javascript:void(0);"
	onclick="vulpe.view.request.submitMenu('/frontend/Community/ajax');"
	title="<fmt:message key='label.site.menu.Community'/>"><span><fmt:message key='label.site.menu.Community'/></span></a></li>
<li><a id="menuCode" href="javascript:void(0);"
	onclick="vulpe.view.request.submitMenu('/frontend/Code/ajax');"
	title="<fmt:message key='label.site.menu.Code'/>"><span><fmt:message key='label.site.menu.Code'/></span></a></li>
<li><a id="menuModules" href="javascript:void(0);"
	onclick="vulpe.view.request.submitMenu('/frontend/Modules/ajax');"
	title="<fmt:message key='label.site.menu.Modules'/>"><span><fmt:message key='label.site.menu.Modules'/></span></a></li>