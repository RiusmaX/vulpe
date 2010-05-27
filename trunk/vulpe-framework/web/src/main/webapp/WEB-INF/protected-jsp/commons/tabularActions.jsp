<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<c:set var="widthIcon"
	value="${vulpeShowAsMobile ? vulpeWidthMobileButtonIcon : vulpeWidthButtonIcon}" />
<c:set var="heightIcon"
	value="${vulpeShowAsMobile ? vulpeHeightMobileButtonIcon : vulpeHeightButtonIcon}" />

<p>
<c:set var="buttonEL"
	value="${'${'}addDetailShow${targetConfig.name}${'}'}" /> 
<c:set
	var="button" value="${util:eval(pageContext, btEL)}" /> 
<c:set
	var="style" value="display: none;" /> 
<c:if test="${bt}">
	<c:set var="style" value="display: inline;" />
</c:if> 
<v:action validate="false" style="${style}"
	labelKey="vulpe.label.addDetail"
	elementId="button_addDetail_${actionConfig.formName}_${targetConfig.name}"
	action="${actionConfig.primitiveActionName}/addDetail/ajax"
	queryString="detail=${targetConfigPropertyName}"
	helpKey="vulpe.help.tabularNew"
	icon="themes/${vulpeTheme}/images/icons/button-add-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" /> 
<c:set var="style"
	value="display: none;" /> 
<c:if test="${prepareShow}">
	<c:set var="style" value="display: inline;" />
</c:if> 
<v:action validate="false" style="${style}"
	labelKey="vulpe.label.refresh"
	elementId="button_prepare_${actionConfig.formName}"
	action="${actionConfig.primitiveActionName}/prepare/ajax"
	helpKey="vulpe.help.refresh"
	icon="themes/${vulpeTheme}/images/icons/button-refresh-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" /> 
<c:set var="style"
	value="display: none;" />
<c:if test="${tabularPostShow}">
	<c:set var="style" value="display: inline;" />
</c:if> 
<v:action validate="true" style="${style}"
	labelKey="vulpe.label.tabularPost"
	elementId="button_tabularPost_${actionConfig.formName}"
	action="${actionConfig.primitiveActionName}/tabularPost/ajax"
	helpKey="vulpe.help.tabularPost"
	icon="themes/${vulpeTheme}/images/icons/button-save-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" />
</p>