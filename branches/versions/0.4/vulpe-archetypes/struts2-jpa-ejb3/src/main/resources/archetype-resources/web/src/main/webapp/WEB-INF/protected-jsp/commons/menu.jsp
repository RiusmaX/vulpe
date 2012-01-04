#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:menu elementId="Index" labelKey="label.${rootArtifactId}.menu.Index">
	<v:menu elementId="Start" labelKey="label.${rootArtifactId}.menu.Index.start" action="/${symbol_dollar}{ever['vulpeCurrentLayout'] == 'FRONTEND' ? 'frontend' : 'backend'}/Index" accesskey="I"/>
</v:menu>
