<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<c:set var="widthIcon"
	value="${vulpeShowAsMobile ? vulpeWidthMobileButtonIcon : vulpeWidthButtonIcon}" />
<c:set var="heightIcon"
	value="${vulpeShowAsMobile ? vulpeHeightMobileButtonIcon : vulpeHeightButtonIcon}" />
<p>
<c:set var="style" value="display: none;" />
<c:if test="${clearShow || SELECT_clearShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action style="${style}" labelKey="vulpe.label.clear"
	elementId="vulpeButtonClear_${vulpeFormName}"
	javascript="document.forms['${vulpeFormName}'].reset();"
	icon="themes/${vulpeTheme}/images/icons/button-clear-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}" showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}" heightIcon="${heightIcon}" accesskey="c" />
<c:set var="style" value="display: none;" />
<c:if test="${prepareShow || SELECT_prepareShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action validate="false" layer="${popup ? popupKey : ''}" style="${style}"
	labelKey="vulpe.label.clear" elementId="vulpeButtonPrepare_${vulpeFormName}"
	action="${controllerConfig.controllerName}/prepare/ajax" helpKey="vulpe.help.clear"
	icon="themes/${vulpeTheme}/images/icons/button-clear-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}" showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}" heightIcon="${heightIcon}" accesskey="c" />
<c:set var="style" value="display: none;" />
<c:if test="${createShow || SELECT_createShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action validate="false" layer="${popup ? popupKey : ''}" style="${style}"
	labelKey="vulpe.label.create" elementId="vulpeButtonCreate_${vulpeFormName}"
	action="${controllerConfig.controllerName}/create/ajax"
	beforeJs="vulpe.view.resetFields(%27${vulpeFormName}%27)" helpKey="vulpe.help.create"
	icon="themes/${vulpeTheme}/images/icons/button-add-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}" showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}" heightIcon="${heightIcon}" accesskey="n" />
<c:set var="style" value="display: none;" />
<c:if test="${readShow || SELECT_readShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action style="${style}" labelKey="vulpe.label.read"
	elementId="vulpeButtonRead_${vulpeFormName}" layer="vulpeSelectTable_${vulpeFormName}"
	action="${controllerConfig.controllerName}/read/ajax"
	beforeJs="vulpe.view.prepareRead(%27${vulpeFormName}%27)" helpKey="vulpe.help.read"
	icon="themes/${vulpeTheme}/images/icons/button-search-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}" showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}" heightIcon="${heightIcon}" accesskey="s" />
<c:set var="style" value="display: none;" />
<c:if test="${reportShow || SELECT_reportShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action style="${style}" labelKey="vulpe.label.report"
	elementId="vulpeButtonRead_${vulpeFormName}" layer="vulpeSelectTable_${vulpeFormName}"
	action="${controllerConfig.reportControllerName}/read/ajax" helpKey="vulpe.help.report"
	icon="themes/${vulpeTheme}/images/icons/button-report-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}" showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}" heightIcon="${heightIcon}" accesskey="r" />
</p>