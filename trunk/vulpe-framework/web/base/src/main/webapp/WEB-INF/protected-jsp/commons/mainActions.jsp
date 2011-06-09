<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsPrepend.jsp"%>
<c:set var="layer" value="body" />
<c:if test="${vulpeBodyTwice}"><c:set var="layer" value="main" /></c:if>
<c:if test="${!onlyToSee}">
	<v:action validate="false" labelKey="clear" helpKey="clear" elementId="Clear" action="clear" icon="clear" iconClass="Clear" config="${util:buttonConfig(pageContext, 'clear', 'MAIN')}" />
	<v:action validate="false" style="${style}" labelKey="create" elementId="Create" action="create" beforeJs="vulpe.view.resetFields(%27${vulpeFormName}%27)" helpKey="create" layer="${layer}" icon="add" iconClass="Create" config="${util:buttonConfig(pageContext, 'create', 'MAIN')}" />
	<v:action labelKey="createPost" elementId="CreatePost" action="createPost"	helpKey="createPost" icon="save" iconClass="CreatePost" config="${util:buttonConfig(pageContext, 'createPost', 'MAIN')}" />
	<v:action validate="false" labelKey="delete" elementId="Delete" action="delete" helpKey="delete" icon="delete" iconClass="Delete" config="${util:buttonConfig(pageContext, 'delete', 'MAIN')}" />
	<v:action labelKey="updatePost"	elementId="UpdatePost" action="updatePost" helpKey="updatePost" icon="save" iconClass="UpdatePost" config="${util:buttonConfig(pageContext, 'updatePost', 'MAIN')}" />
	<v:action labelKey="clone" elementId="Clone" action="cloneIt" helpKey="clone" icon="clone" iconClass="Clone" config="${util:buttonConfig(pageContext, 'clone', 'MAIN')}" />
</c:if>
<c:if test="${util:isButtonRender(pageContext, 'back', 'MAIN')}">
	<c:set var="action"	value="${controllerConfig.ownerController}/select/ajax${operation == 'UPDATE' || operation == 'UPDATE_POST' ? '?back=true' : ''}" />
	<v:action validate="false" labelKey="back" elementId="Prepare" action="${not empty urlBack ? urlBack : action}"	layer="${not empty layerUrlBack ? layerUrlBack : ''}" layerFields="this" helpKey="back" icon="back" iconClass="Back" config="${util:buttonConfig(pageContext, 'back', 'MAIN')}" />
	<c:remove var="urlBack" scope="session" />
	<c:remove var="layerUrlBack" scope="session" />
</c:if>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsAppend.jsp"%>
</p>