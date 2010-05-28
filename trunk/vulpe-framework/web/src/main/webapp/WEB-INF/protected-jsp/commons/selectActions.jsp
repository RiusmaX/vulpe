<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<c:set var="widthIcon"
	value="${vulpeShowAsMobile ? vulpeWidthMobileButtonIcon : vulpeWidthButtonIcon}" />
<c:set var="heightIcon"
	value="${vulpeShowAsMobile ? vulpeHeightMobileButtonIcon : vulpeHeightButtonIcon}" />
<p>
<c:set var="style" value="display: inline;" /> 
<c:if test="${clearShow == false}">
	<c:set var="style" value="display: none;" />
</c:if> 
<v:action style="${style}" labelKey="vulpe.label.clear"
	elementId="vulpeButtonClear"
	javascript="document.forms['${actionConfig.formName}'].reset();"
	icon="themes/${vulpeTheme}/images/icons/button-clear-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}" showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}" heightIcon="${heightIcon}" accesskey="c" />
<c:set var="style" value="display: none;" />
<c:if test="${prepareShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action validate="false" layer="${popup ? popupKey : ''}" style="${style}"
	labelKey="vulpe.label.clear" elementId="vulpeButtonPrepare"
	action="${actionConfig.primitiveActionName}/prepare/ajax" helpKey="vulpe.help.clear"
	icon="themes/${vulpeTheme}/images/icons/button-clear-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}" showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}" heightIcon="${heightIcon}" accesskey="c" /> 
<c:set var="style" value="display: none;" />
<c:if test="${createShow}">
	<c:set var="style" value="display: inline;" />
</c:if> 
<v:action validate="false" layer="${popup ? popupKey : ''}" style="${style}"
	labelKey="vulpe.label.create" elementId="vulpeButtonCreate"
	action="${actionConfig.primitiveActionName}/create/ajax"
	beforeJs="vulpe.view.resetFields(%27${actionConfig.formName}%27)" helpKey="vulpe.help.create"
	icon="themes/${vulpeTheme}/images/icons/button-add-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}" showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}" heightIcon="${heightIcon}" accesskey="n" />
<c:set var="style" value="display: none;" />
<c:if test="${readShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action style="${style}" labelKey="vulpe.label.read"
	elementId="vulpeButtonRead" layer="vulpeSelectTable"
	action="${actionConfig.primitiveActionName}/read/ajax"
	beforeJs="vulpe.view.prepareRead(%27${actionConfig.formName}%27)" helpKey="vulpe.help.read"
	icon="themes/${vulpeTheme}/images/icons/button-search-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}" showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}" heightIcon="${heightIcon}" accesskey="s" /> 
<c:set var="style" value="display: none;" />
<c:if test="${reportShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action style="${style}" labelKey="vulpe.label.report"
	elementId="vulpeButtonRead" layer="vulpeSelectTable"
	action="${actionConfig.primitiveReportActionName}/read/ajax" helpKey="vulpe.help.report"
	icon="themes/${vulpeTheme}/images/icons/button-report-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}" showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}" heightIcon="${heightIcon}" accesskey="r" />
</p>