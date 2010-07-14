<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<c:set var="widthIcon" value="${vulpeShowAsMobile ? vulpeWidthMobileButtonIcon : vulpeWidthButtonIcon}" />
<c:set var="heightIcon" value="${vulpeShowAsMobile ? vulpeHeightMobileButtonIcon : vulpeHeightButtonIcon}" />

<p>
<c:set var="style" value="display: none;" />
<c:if test="${tabularFilterShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action validate="false" style="${style}"
	labelKey="vulpe.label.tabularFilter"
	elementId="vulpeButtonTabularFilter_${vulpeFormName}"
	action="${controllerConfig.controllerName}/tabularFilter/ajax"
	helpKey="vulpe.help.tabularFilter"
	icon="themes/${vulpeTheme}/images/icons/button-filter-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" />
<c:set var="buttonEL" value="${'${'}addDetailShow${targetConfig.name}${'}'}" />
<c:set var="button" value="${util:eval(pageContext, buttonEL)}" />
<c:set var="style" value="display: none;" />
<c:if test="${button}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action validate="false" style="${style}"
	labelKey="vulpe.label.addDetail"
	elementId="vulpeButtonAddDetail_${vulpeFormName}_${targetConfig.name}"
	action="${controllerConfig.controllerName}/addDetail/ajax"
	queryString="detail=${targetConfigPropertyName}"
	helpKey="vulpe.help.tabularNew"
	icon="themes/${vulpeTheme}/images/icons/button-add-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" />
<c:set var="style" value="display: none;" />
<c:if test="${prepareShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action validate="false" style="${style}"
	labelKey="vulpe.label.tabularRefresh"
	elementId="vulpeButtonTabularRefresh_${vulpeFormName}"
	action="${controllerConfig.controllerName}/tabular/ajax"
	helpKey="vulpe.help.tabularRefresh"
	icon="themes/${vulpeTheme}/images/icons/button-refresh-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" />
<c:set var="style" value="display: none;" />
<c:if test="${tabularPostShow}">
	<c:set var="style" value="display: inline;" />
</c:if>
<v:action validate="true" style="${style}"
	labelKey="vulpe.label.tabularPost"
	elementId="vulpeButtonTabularPost_${vulpeFormName}"
	action="${controllerConfig.controllerName}/tabularPost/ajax"
	helpKey="vulpe.help.tabularPost"
	icon="themes/${vulpeTheme}/images/icons/button-save-${widthIcon}x${heightIcon}.png"
	showButtonAsImage="${vulpeShowButtonAsImage}"
	showButtonIcon="${vulpeShowButtonIcon}"
	showButtonText="${vulpeShowButtonText}" widthIcon="${widthIcon}"
	heightIcon="${heightIcon}" />
</p>