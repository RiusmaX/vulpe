#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:menu labelKey="label.${rootArtifactId}.menu.Index">
	<v:menu labelKey="label.${rootArtifactId}.menu.Index.start" action="/frontend/Index" accesskey="I"/>
</v:menu>