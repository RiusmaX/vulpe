<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<c:set var="widthIcon" value="${vulpeShowAsMobile ? vulpeWidthMobileButtonIcon : vulpeWidthButtonIcon}" />
<c:set var="heightIcon"	value="${vulpeShowAsMobile ? vulpeHeightMobileButtonIcon : vulpeHeightButtonIcon}" />

<p>
<c:set var="style" value="display: none;" />
<c:if test="${clearShow || CRUD_clearShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action style="${style}" labelKey="vulpe.label.clear"
	elementId="vulpeButtonClear_${vulpeFormName}"
	javascript="document.forms['${vulpeFormName}'].reset();"
	icon="themes/${vulpeTheme}/images/icons/button-clear-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" />
<c:set var="style" value="display: none;" />
<c:if test="${createShow || CRUD_createShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action validate="false" style="${style}"
	labelKey="vulpe.label.create"
	elementId="vulpeButtonCreate_${vulpeFormName}"
	action="${controllerConfig.controllerName}/create/ajax"
	beforeJs="vulpe.view.resetFields(%27${vulpeFormName}%27)"
	helpKey="vulpe.help.create"
	icon="themes/${vulpeTheme}/images/icons/button-add-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" />
<c:set var="style" value="display: none;" />
<c:if test="${createPostShow || CRUD_createPostShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action style="${style}" labelKey="vulpe.label.createPost"
	elementId="vulpeButtonCreatePost_${vulpeFormName}"
	action="${controllerConfig.controllerName}/createPost/ajax"
	helpKey="vulpe.help.createPost"
	icon="themes/${vulpeTheme}/images/icons/button-save-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" />
<c:set var="style" value="display: none;" />
<c:if test="${deleteShow || CRUD_deleteShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action beforeJs="vulpe.view.confirmExclusion()" validate="false"
	style="${style}" labelKey="vulpe.label.delete"
	elementId="vulpeButtonDelete_${vulpeFormName}"
	action="${controllerConfig.controllerName}/delete/ajax"
	helpKey="vulpe.help.delete"
	icon="themes/${vulpeTheme}/images/icons/button-delete-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" />
<c:set var="style" value="display: none;" />
<c:if test="${updatePostShow || CRUD_updatePostShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action style="${style}" labelKey="vulpe.label.updatePost"
	elementId="vulpeButtonUpdatePost_${vulpeFormName}"
	action="${controllerConfig.controllerName}/updatePost/ajax"
	helpKey="vulpe.help.updatePost"
	icon="themes/${vulpeTheme}/images/icons/button-save-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" />
<c:set var="style" value="display: none;" />
<c:if test="${prepareShow || CRUD_prepareShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<c:set var="action"	value="${controllerConfig.ownerController}/select/ajax${operation == 'update' || operation == 'updatePost' ? '?back=true' : ''}" />
<v:action
	validate="false" style="${style}" labelKey="vulpe.label.prepare"
	elementId="vulpeButtonPrepare_${vulpeFormName}"
	action="${not empty urlBack ? urlBack : action}"
	layer="${not empty layerUrlBack ? layerUrlBack : layer}"
	helpKey="vulpe.help.prepare"
	icon="themes/${vulpeTheme}/images/icons/button-back-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" />
<c:remove var="urlBack" scope="session" />
<c:remove var="layerUrlBack" scope="session" />
</p>