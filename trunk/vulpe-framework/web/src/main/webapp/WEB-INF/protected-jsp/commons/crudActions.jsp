<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/crudActionsExtended.jsp"%>
<c:set var="layer" value="body" />
<c:if test="${vulpeBodyTwice}">
	<c:set var="layer" value="crud" />
</c:if>
<c:if test="${!onlyToSee}">
	<c:if test="${now['buttons']['clear'] || now['buttons']['CRUD_clear']}">
		<v:action labelKey="clear" helpKey="clear" elementId="Clear" javascript="document.forms['${vulpeFormName}'].reset();" icon="clear" iconClass="Clear"/>
	</c:if>
	<c:if test="${now['buttons']['create'] || now['buttons']['CRUD_create']}">
		<v:action validate="false" style="${style}" labelKey="create" elementId="Create" action="create" beforeJs="vulpe.view.resetFields(%27${vulpeFormName}%27)" helpKey="create" layer="${layer}" icon="add" iconClass="Create" />
	</c:if>
	<c:if test="${now['buttons']['createPost'] || now['buttons']['CRUD_createPost']}">
		<v:action labelKey="createPost" elementId="CreatePost" action="createPost"	helpKey="createPost" icon="save" iconClass="CreatePost" />
	</c:if>
	<c:if test="${now['buttons']['delete'] || now['buttons']['CRUD_delete']}">
		<v:action beforeJs="vulpe.view.confirmExclusion()" validate="false"	labelKey="delete" elementId="Delete" action="delete" helpKey="delete" icon="delete" iconClass="Delete" />
	</c:if>
	<c:if test="${now['buttons']['updatePost'] || now['buttons']['CRUD_updatePost']}">
		<v:action labelKey="updatePost"	elementId="UpdatePost"	action="updatePost"	helpKey="updatePost" icon="save" iconClass="UpdatePost" />
	</c:if>
</c:if>
<c:if test="${now['buttons']['prepare'] || now['buttons']['CRUD_prepare']}">
	<c:set var="action"	value="${controllerConfig.ownerController}/select/ajax${operation == 'UPDATE' || operation == 'UPDATE_POST' ? '?back=true' : ''}" />
	<v:action validate="false" labelKey="prepare" elementId="Prepare" action="${not empty urlBack ? urlBack : action}"	layer="${not empty layerUrlBack ? layerUrlBack : ''}" helpKey="prepare" icon="back" iconClass="Back" />
	<c:remove var="urlBack" scope="session" />
	<c:remove var="layerUrlBack" scope="session" />
</c:if>
</p>