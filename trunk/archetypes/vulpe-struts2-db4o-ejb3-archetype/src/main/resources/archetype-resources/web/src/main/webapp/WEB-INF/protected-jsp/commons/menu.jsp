#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<li><a id="menuVulpe" href="javascript:void(0);" class="current"
	onclick="vulpe.view.request.submitMenu('/frontend/Index/ajax.action');"
	title="<fmt:message key='label.${rootArtifactId}.menu.Index'/>"><span><fmt:message key='label.${rootArtifactId}.menu.Index'/></span></a></li>