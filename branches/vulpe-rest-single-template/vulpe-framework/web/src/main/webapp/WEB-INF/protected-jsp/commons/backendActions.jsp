<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<p>
	<c:set var="style" value="display: none;"/>
	<c:if test="${clearShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.clear" elementId="vulpeButtonClear_${controllerConfig.formName}" javascript="document.forms['${controllerConfig.formName}'].reset();"/>

	<c:set var="style" value="display: none;"/>
	<c:if test="${createShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action validate="false" style="${style}" labelKey="vulpe.label.create" elementId="vulpeButtonCreate_${controllerConfig.formName}" action="${controllerConfig.controllerName}/create" beforeJs="vulpe.view.resetFields(%27${controllerConfig.formName}%27)" helpKey="vulpe.help.create" />

	<c:set var="style" value="display: none;"/>
	<c:if test="${createPostShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.createPost" elementId="vulpeButtonCreatePost_${controllerConfig.formName}" action="${controllerConfig.controllerName}/createPost" helpKey="vulpe.help.createPost"/>

	<c:set var="style" value="display: none;"/>
	<c:if test="${deleteShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action beforeJs="vulpe.view.confirmExclusion()" validate="false" style="${style}" labelKey="vulpe.label.delete" elementId="vulpeButtonDelete_${controllerConfig.formName}" action="${controllerConfig.controllerName}/delete" helpKey="vulpe.help.delete"/>

	<c:set var="style" value="display: none;"/>
	<c:if test="${updatePostShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action style="${style}" labelKey="vulpe.label.updatePost" elementId="vulpeButtonUpdatePost_${controllerConfig.formName}" action="${controllerConfig.controllerName}/updatePost" helpKey="vulpe.help.updatePost"/>

	<c:set var="style" value="display: none;"/>
	<c:if test="${prepareShow}">
		<c:set var="style" value="display: inline;"/>
	</c:if>
	<v:action validate="false" style="${style}" labelKey="vulpe.label.prepare" elementId="vulpeButtonPrepare_${controllerConfig.formName}" action="${controllerConfig.controllerName}/prepare" helpKey="vulpe.help.prepare"/>

</p>