<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp"%>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsPrepend.jsp"%>
<c:set var="layer" value="body" />
<c:if test="${vulpeBodyTwice}"><c:set var="layer" value="main" /></c:if>
<c:if test="${!onlyToSee}">
	<v:action labelKey="clear" helpKey="clear" elementId="Clear" javascript="document.forms['${vulpeFormName}'].reset();" icon="clear" iconClass="Clear" show="${now['buttons']['clear'] || now['buttons']['Main_clear']}"/>
	<v:action validate="false" style="${style}" labelKey="create" elementId="Create" action="create" beforeJs="vulpe.view.resetFields(%27${vulpeFormName}%27)" helpKey="create" layer="${layer}" icon="add" iconClass="Create" show="${now['buttons']['create'] || now['buttons']['Main_create']}" />
	<v:action labelKey="createPost" elementId="CreatePost" action="createPost"	helpKey="createPost" icon="save" iconClass="CreatePost" show="${now['buttons']['createPost'] || now['buttons']['Main_createPost']}" />
	<v:action beforeJs="vulpe.view.confirmExclusion()" validate="false"	labelKey="delete" elementId="Delete" action="delete" helpKey="delete" icon="delete" iconClass="Delete" show="${now['buttons']['delete'] || now['buttons']['Main_delete']}" />
	<v:action labelKey="updatePost"	elementId="UpdatePost"	action="updatePost"	helpKey="updatePost" icon="save" iconClass="UpdatePost" show="${now['buttons']['updatePost'] || now['buttons']['Main_updatePost']}" />
</c:if>
<c:if test="${now['buttons']['prepare'] || now['buttons']['Main_prepare']}">
	<c:set var="action"	value="${controllerConfig.ownerController}/select/ajax${operation == 'UPDATE' || operation == 'UPDATE_POST' ? '?back=true' : ''}" />
	<v:action validate="false" labelKey="prepare" elementId="Prepare" action="${not empty urlBack ? urlBack : action}"	layer="${not empty layerUrlBack ? layerUrlBack : ''}" helpKey="prepare" icon="back" iconClass="Back" />
	<c:remove var="urlBack" scope="session" />
	<c:remove var="layerUrlBack" scope="session" />
</c:if>
<%@include file="/WEB-INF/protected-jsp/commons/mainActionsAppend.jsp"%>
</p>