#set( $symbol_pound = '#' ) 
#set( $symbol_dollar = '$' ) 
#set( $symbol_escape = '\' )
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<li><a href="javascript:void(0);" class="current"
	title="<fmt:message key='label.${rootArtifactId}.menu.Index'/>"><span><fmt:message
	key='label.${rootArtifactId}.menu.Index' /></span></a>
<ul>
	<li><a href="javascript:void(0);" class="current"
		onclick="vulpe.view.request.submitMenu('/frontend/Index/ajax');"
		title="<fmt:message key='label.${rootArtifactId}.menu.Index.start'/>"><span><fmt:message
		key='label.${rootArtifactId}.menu.Index.start' /></span></a></li>
</ul>
</li>