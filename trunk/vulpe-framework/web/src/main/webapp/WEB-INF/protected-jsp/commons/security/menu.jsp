<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<li><a href="javascript:void(0);" title="<fmt:message key='label.vulpe.menu.Security'/>"><fmt:message key='label.vulpe.menu.Security'/></a>
<ul>
	<li><a href="javascript:void(0);"
		onclick="javascript:vulpe.view.request.submitMenu('/security/Role/tabular/prepare/ajax');"
		title="<fmt:message key='label.vulpe.menu.Security.Role'/>"><fmt:message key='label.vulpe.menu.Security.Role'/></a></li>
	<li><a href="javascript:void(0);"
		onclick="javascript:vulpe.view.request.submitMenu('/security/User/select/prepare/ajax');"
		title="<fmt:message key='label.vulpe.menu.Security.User'/>"><fmt:message key='label.vulpe.menu.Security.User'/></a></li>
	<li><a href="javascript:void(0);"
		onclick="javascript:vulpe.view.request.submitMenu('/security/SecureResource/select/prepare/ajax');"
		title="<fmt:message key='label.vulpe.menu.Security.Resource'/>"><fmt:message key='label.vulpe.menu.Security.Resource'/></a></li>
</ul>
</li>