<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<li><a href="javascript:void(0);" title="<fmt:message key='label.vulpe.menu.Audit'/>"><fmt:message key='label.vulpe.menu.Audit'/></a>
<ul>
	<li><a
		href="javascript:void(0);"
		onclick="javascript:vulpe.view.request.submitMenu('/audit/Occurrence/select/ajax');"
		title="<fmt:message key='label.vulpe.menu.Audit.Occurrence'/>"><fmt:message key='label.vulpe.menu.Audit.Occurrence'/></a></li>
</ul>
</li>