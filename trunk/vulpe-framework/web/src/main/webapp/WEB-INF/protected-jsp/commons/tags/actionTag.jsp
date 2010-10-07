<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${show}">
	<c:if test="${empty showButtonAsImage}"><c:set var="showButtonAsImage" value="${global['showButtonAsImage']}" /></c:if>
	<c:if test="${empty showButtonIcon}"><c:set var="showButtonIcon" value="${global['showButtonIcon']}" /></c:if>
	<c:if test="${empty showButtonText}"><c:set var="showButtonText" value="${global['showButtonText']}" /></c:if>
	<c:if test="${not empty beforeJs && fn:contains(beforeJs, 'vulpe.view.confirmExclusion()')}">
		<c:set var="showDeleteConfirmation" value="true" />
		<c:set var="beforeJs" value="" />
	</c:if>
	<c:set var="buttonPrefix" value="vulpeButton" />
	<c:set var="labelKeyPrefix" value="label.vulpe." />
	<c:if test="${not empty labelKey && !fn:contains(labelKey, '.')}"><c:set var="labelKey" value="${labelKeyPrefix}${labelKey}" /></c:if>
	<c:set var="helpKeyPrefix" value="help.vulpe." />
	<c:if test="${not empty helpKey && !fn:contains(helpKey, '.')}"><c:set var="helpKey" value="${helpKeyPrefix}${helpKey}" /></c:if>
	<c:if test="${empty layerFields}"><c:set var="layerFields" value="${vulpeFormName}" /></c:if>
	<c:if test="${layerFields eq 'false'}"><c:set var="layerFields" value="" /></c:if>
	<c:if test="${empty validate}"><c:set var="validate" value="true" /></c:if>
	<c:if test="${empty layer}"><c:set var="layer" value="body" /></c:if>
	<c:choose>
		<c:when test="${empty elementId}"><c:set var="elementId" value="${labelKey}" /></c:when>
		<c:when test="${!fn:contains(elementId, 'vulpeButton')}"><c:set var="elementId" value="${buttonPrefix}${elementId}-${vulpeFormName}" /></c:when>
	</c:choose>
	<c:if test="${not empty action && !fn:contains(action, '/')}"><c:set var="action" value="${controllerConfig.controllerName}/${action}/ajax"/></c:if>
	<c:if test="${empty javascript}">
		<c:choose>
			<c:when test="${empty action}"><c:set var="javascript" value="${showDeleteConfirmation ? 'vulpe.view.confirmExclusion(function(){': ''}vulpe.view.request.submitForm({formName: '${vulpeFormName}', layerFields: '${layerFields}', queryString: '${queryString}', layer: '${layer}', validate: ${validate}, beforeJs: '${fn:escapeXml(beforeJs)}', afterJs: '${fn:escapeXml(afterJs)}', isFile: false});${showDeleteConfirmation ? '})': ''}" /></c:when>
			<c:when test="${!noSubmitForm}"><c:set var="javascript" value="${showDeleteConfirmation ? 'vulpe.view.confirmExclusion(function(){': ''}vulpe.view.request.submitFormAction({url:'${action}', formName: '${vulpeFormName}', layerFields: '${layerFields}', queryString: '${queryString}', layer: '${layer}', validate: ${validate}, beforeJs: '${fn:escapeXml(beforeJs)}', afterJs: '${fn:escapeXml(afterJs)}'});${showDeleteConfirmation ? '})': ''}" /></c:when>
			<c:otherwise><c:set var="javascript" value="${showDeleteConfirmation ? 'vulpe.view.confirmExclusion(function(){': ''}vulpe.view.request.submitPage({url: '${action}', queryString: '${queryString}', layer: '${layer}', beforeJs: '${fn:escapeXml(beforeJs)}', afterJs: '${fn:escapeXml(afterJs)}'});${showDeleteConfirmation ? '})': ''}" /></c:otherwise>
		</c:choose>
	</c:if>
	<c:choose>
		<c:when test="${!showButtonAsImage}">
			<c:if test="${empty styleClass}"><c:set var="styleClass" value="vulpeSubmit" /></c:if>
			<input style="${style}" id="${elementId}" type="button" value="<fmt:message key="${labelKey}"/>" class="${styleClass}" onclick="${javascript}" title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty icon}">
				<c:if test="${empty widthIcon}"><c:set var="widthIcon" value="${global['showAsMobile'] ? global['widthMobileButtonIcon'] : global['widthButtonIcon']}" /></c:if>
				<c:if test="${empty heightIcon}"><c:set var="heightIcon" value="${global['showAsMobile'] ? global['heightMobileButtonIcon'] : global['heightButtonIcon']}" /></c:if>
				<c:if test="${empty borderIcon}"><c:set var="borderIcon" value="0" /></c:if>
				<c:set var="iconPrefix"	value="themes/${global['theme']}/images/icons/button" />
				<c:set var="icon" value="${iconPrefix}-${icon}-${widthIcon}x${heightIcon}.png" />
				<c:if test="${!fn:startsWith(icon, pageContext.request.contextPath)}"><c:set var="icon" value="${pageContext.request.contextPath}/${icon}" /></c:if>
			</c:if>
			<c:choose>
				<c:when test="${showButtonAsImage}">
					<c:choose>
						<c:when test="${fn:contains(javascript, 'Popup')}">
							<c:if test="${empty iconClass}"><c:set var="iconClass" value="vulpeImagePopupButton" /></c:if>
							<img class="${iconClass}" style="${style}" id="${elementId}" src="${icon}" title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" width="${widthIcon}" height="${heightIcon}" border="${borderIcon}" onclick="${javascript}" /><c:if test="${showButtonText}">&nbsp;<fmt:message key="${labelKey}" /></c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${not empty iconClass}"><c:set var="iconClass" value="${buttonPrefix}${iconClass}" /></c:if>
							<a id="${elementId}" class="${styleClass}" style="${style}" accesskey="${accesskey}" href="javascript:void(0);" onclick="${javascript}"><c:if test="${not empty icon}"><img class="${iconClass}" src="${icon}" title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" width="${widthIcon}" height="${heightIcon}" border="${borderIcon}" /></c:if><c:if test="${showButtonText}">${not empty icon ? '&nbsp;' : ''}<fmt:message key="${labelKey}" /></c:if></a>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${showButtonIcon}"><button style="${style}" id="${elementId}" type="button" accesskey="${accesskey}" value="<fmt:message key="${labelKey}"/>" class="${styleClass}" onclick="${javascript}" title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>"><img class="${iconClass}" src="${icon}" title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" width="${widthIcon}" height="${heightIcon}" border="${borderIcon}" /><c:if test="${showButtonText}">&nbsp;<fmt:message key="${labelKey}" /></c:if></button></c:when>
						<c:otherwise>
							<c:set var="styleClass" value="vulpeSubmit" />
							<input style="${style}" id="${elementId}" type="button" accesskey="${accesskey}" value="<fmt:message key="${labelKey}"/>" class="${styleClass}" onclick="${javascript}" title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" />
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	<c:if test="${not empty hotKey}">
	<script type="text/javascript">
	$(document).ready(function() {
		vulpe.util.addHotKey({
			hotKey: "${hotKey}",
			command: function (evt){
				vulpe.util.get("${elementId}").click();
				return false;
			}
		});
	});
	</script>
	</c:if>
</c:if>