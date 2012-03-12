<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsPrepend.jsp"%>
<c:set var="layer" value="body" />
<c:if test="${now['bodyTwice']}"><c:set var="layer" value="main" /></c:if>
<c:if test="${!now['onlyToSee']}">
	<v:action validate="false" labelKey="clear" helpKey="clear" elementId="Clear" action="clear" icon="clear" iconClass="Clear" config="${util:buttonConfig(pageContext, 'clear', 'MAIN')}" layerFields="this" layer="${now['popup'] ? now['popupKey'] : layer}" />
	<v:action validate="false" style="${style}" labelKey="create" elementId="Create" action="create" beforeJs="vulpe.view.resetFields(%27${vulpeFormName}%27)" helpKey="create" layer="${layer}" icon="add" iconClass="Create" config="${util:buttonConfig(pageContext, 'create', 'MAIN')}" layerFields="this" />
	<v:action labelKey="createPost" elementId="CreatePost" action="createPost"	helpKey="createPost" icon="save" iconClass="CreatePost" config="${util:buttonConfig(pageContext, 'createPost', 'MAIN')}" layer="${now['popup'] ? now['popupKey'] : layer}" />
	<v:action validate="false" labelKey="delete" elementId="Delete" action="delete" helpKey="delete" icon="delete" iconClass="Delete" config="${util:buttonConfig(pageContext, 'delete', 'MAIN')}" />
	<v:action labelKey="updatePost"	elementId="UpdatePost" action="updatePost" helpKey="updatePost" icon="save" iconClass="UpdatePost" config="${util:buttonConfig(pageContext, 'updatePost', 'MAIN')}" layer="${now['popup'] ? now['popupKey'] : layer}" />
	<v:action labelKey="clone" elementId="Clone" action="cloneIt" helpKey="clone" icon="clone" iconClass="Clone" config="${util:buttonConfig(pageContext, 'clone', 'MAIN')}" />
</c:if>
<c:if test="${util:isButtonRender(pageContext, 'back', 'MAIN')}">
	<c:set var="action"	value="${now['controllerConfig'].ownerController}/select/ajax${now['operation'] == 'UPDATE' || now['operation'] == 'UPDATE_POST' ? '?now.back=true' : ''}" />
	<v:action validate="false" labelKey="back" elementId="Prepare" action="${not empty now['urlBack'] ? now['urlBack'] : action}"	layer="${not empty now['layerURLBack'] ? now['layerURLBack'] : ''}" layerFields="this" helpKey="back" icon="back" iconClass="Back" config="${util:buttonConfig(pageContext, 'back', 'MAIN')}" />
</c:if>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsAppend.jsp"%>
</p>