<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>

<p>
	<c:set var="style" value="display: inline;"/>
	<c:if test="${clearShow == false}">
		<c:set var="style" value="display: none;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.clear" elementId="bt_clear_${actionConfig.formName}" javascript="document.forms['${actionConfig.formName}'].reset();"/>

	<c:set var="style" value="display: none;"/>
	<c:if test="${createShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action validate="false" style="${style}" labelKey="vulpe.label.create" elementId="bt_create_${actionConfig.formName}" action="${actionConfig.primitiveActionName}/create" beforeJs="vulpe.view.resetFields(%27${actionConfig.formName}%27)" helpKey="vulpe.help.create" />

	<c:set var="style" value="display: none;"/>
	<c:if test="${createPostShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.createPost" elementId="bt_create_post_${actionConfig.formName}" action="${actionConfig.primitiveActionName}/createPost" helpKey="vulpe.help.createPost"/>

	<c:set var="style" value="display: none;"/>
	<c:if test="${deleteShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action beforeJs="vulpe.view.confirmExclusion()" validate="false" style="${style}" labelKey="vulpe.label.delete" elementId="bt_delete_${actionConfig.formName}" action="${actionConfig.primitiveActionName}/delete" helpKey="vulpe.help.delete"/>

	<c:set var="style" value="display: none;"/>
	<c:if test="${updatePostShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.updatePost" elementId="bt_update_post_${actionConfig.formName}" action="${actionConfig.primitiveActionName}/updatePost" helpKey="vulpe.help.updatePost"/>

	<c:set var="style" value="display: inline;"/>
	<c:if test="${prepareShow == false}">
		<c:set var="style" value="display: none;"/>
	</c:if>
	<v:action validate="false" style="${style}" labelKey="vulpe.label.prepare" elementId="bt_prepare_${actionConfig.formName}" action="${actionConfig.primitiveActionName}/prepare" helpKey="vulpe.help.prepare"/>

</p>