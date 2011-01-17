<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${render}">
	<c:if test="${empty showButtonsAsImage}"><c:set var="showButtonsAsImage" value="${global['showButtonsAsImage']}" /></c:if>
	<c:if test="${empty showIconOfButton}"><c:set var="showIconOfButton" value="${global['showIconOfButton']}" /></c:if>
	<c:if test="${empty showTextOfButton}"><c:set var="showTextOfButton" value="${global['showTextOfButton']}" /></c:if>
	<c:if test="${global['showWarningBeforeDelete'] && action == 'delete'}">
		<c:set var="showWarningBeforeDelete" value="true" />
		<c:set var="beforeJs" value="" />
	</c:if>
	<c:if test="${global['showWarningBeforeUpdatePost'] && action == 'updatePost'}">
		<c:set var="showWarningBeforeUpdatePost" value="true" />
		<c:set var="beforeJs" value="" />
	</c:if>
	<c:if test="${global['showWarningBeforeClear'] && action == 'clear'}">
		<c:set var="showWarningBeforeClear" value="true" />
		<c:set var="beforeJs" value="" />
	</c:if>
	<c:if test="${global['showWarningBeforeDelete'] && action == 'tabularPost'}">
		<c:set var="showDeleteWarningBeforeTabularPost" value="true" />
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
	<c:if test="${not empty queryString}"><c:set var="queryString" value=", queryString: '${queryString}'"/></c:if>
	<c:if test="${not empty validate}"><c:set var="validate" value=", validate: ${validate}"/></c:if>
	<c:if test="${not empty afterJs}"><c:set var="afterJs" value=", afterJs: '${fn:escapeXml(afterJs)}'"/></c:if>
	<c:if test="${not empty beforeJs}"><c:set var="beforeJs" value=", beforeJs: '${fn:escapeXml(beforeJs)}'"/></c:if>
	<c:if test="${not empty action && !fn:contains(action, '/')}"><c:set var="action" value="${controllerConfig.controllerName}/${action}/ajax"/></c:if>
	<c:if test="${empty javascript}">
		<c:choose>
			<c:when test="${empty action}">
				<c:if test="${showWarningBeforeDelete}"><c:set var="confirmDelete" value="vulpe.view.confirm('delete', function(){"/></c:if>
				<c:set var="javascript" value="${showWarningBeforeDelete ? confirmDelete : ''}vulpe.view.request.submitAjax({layerFields: '${layerFields}', layer: '${layer}'${queryString}${validate}${beforeJs}${afterJs}, isFile: false});${showWarningBeforeDelete ? '})': ''}" />
			</c:when>
			<c:when test="${!noSubmitForm && showDeleteWarningBeforeTabularPost}"><c:set var="javascript" value="vulpe.view.validateSelectedToDelete(function(){vulpe.view.request.submitAjaxAction({url:'${action}', layerFields: '${layerFields}', layer: '${layer}'${queryString}${validate}${beforeJs}${afterJs}});})" /></c:when>
			<c:when test="${!noSubmitForm && showWarningBeforeDelete}"><c:set var="javascript" value="vulpe.view.confirm('delete', function(){vulpe.view.request.submitAjaxAction({url:'${action}', layerFields: '${layerFields}', layer: '${layer}'${queryString}${validate}${beforeJs}${afterJs}});})" /></c:when>
			<c:when test="${!noSubmitForm && showWarningBeforeUpdatePost}"><c:set var="javascript" value="vulpe.view.confirm('updatePost', function(){vulpe.view.request.submitAjaxAction({url:'${action}', layerFields: '${layerFields}', layer: '${layer}'${queryString}${validate}${beforeJs}${afterJs}});})" /></c:when>
			<c:when test="${!noSubmitForm && showWarningBeforeClear}"><c:set var="javascript" value="vulpe.view.confirm('clear', function(){vulpe.view.request.submitAjaxAction({url:'${action}', layerFields: '${layerFields}', layer: '${layer}'${queryString}${validate}${beforeJs}${afterJs}});})" /></c:when>
			<c:otherwise>
				<c:if test="${showWarningBeforeDelete}"><c:set var="confirmDelete" value="vulpe.view.confirm('delete', function(){"/></c:if>
				<c:set var="javascript" value="${showWarningBeforeDelete ? confirmDelete : ''}vulpe.view.request.submitAjaxAction({url: '${action}', layerFields: '${layerFields}', layer: '${layer}'${queryString}${validate}${beforeJs}${afterJs}});${showWarningBeforeDelete ? '})': ''}" />
			</c:otherwise>
		</c:choose>
	</c:if>
	<c:choose>
		<c:when test="${!showButtonsAsImage}">
			<c:if test="${empty styleClass}"><c:set var="styleClass" value="vulpeSubmit" /></c:if>
			<input style="${style}" id="${elementId}" type="button" value="<fmt:message key="${labelKey}"/>" class="${styleClass}" onclick="${javascript}" title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" />
		</c:when>
		<c:otherwise>
			<c:if test="${not empty icon}">
				<c:if test="${empty iconWidth}"><c:set var="iconWidth" value="${global['showAsMobile'] ? global['mobileIconWidth'] : global['iconWidth']}" /></c:if>
				<c:if test="${empty iconHeight}"><c:set var="iconHeight" value="${global['showAsMobile'] ? global['mobileIconHeight'] : global['iconHeight']}" /></c:if>
				<c:if test="${empty iconBorder}"><c:set var="iconBorder" value="0" /></c:if>
				<c:if test="${empty iconExtension}"><c:set var="iconExtension" value="png" /></c:if>
				<c:set var="iconPrefix"	value="themes/${global['theme']}/images/icons/button" />
				<c:set var="icon" value="${iconPrefix}-${icon}-${iconWidth}x${iconHeight}.${iconExtension}" />
				<c:if test="${!fn:startsWith(icon, pageContext.request.contextPath)}"><c:set var="icon" value="${pageContext.request.contextPath}/${icon}" /></c:if>
			</c:if>
			<c:choose>
				<c:when test="${showButtonsAsImage}">
					<c:choose>
						<c:when test="${fn:contains(javascript, 'Popup')}">
							<c:if test="${empty iconClass}"><c:set var="iconClass" value="vulpeImagePopupButton" /></c:if>
							<img class="${iconClass}" style="${style}" id="${elementId}" src="${icon}" title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" alt="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" width="${iconWidth}" height="${iconHeight}" onclick="${javascript}" /><c:if test="${showTextOfButton}">&nbsp;<fmt:message key="${labelKey}" /></c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${not empty iconClass}"><c:set var="iconClass" value="${buttonPrefix}${iconClass}" /></c:if>
							<a id="${elementId}" class="${styleClass}" style="${style}" accesskey="${accesskey}" href="javascript:void(0);" onclick="${javascript}"><c:if test="${not empty icon}"><img class="${iconClass}" src="${icon}" title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" alt="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" width="${iconWidth}" height="${iconHeight}" /></c:if><c:if test="${showTextOfButton}">${not empty icon ? '&nbsp;' : ''}<fmt:message key="${labelKey}" /></c:if></a>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${showIconOfButton}"><button style="${style}" id="${elementId}" type="button" accesskey="${accesskey}" value="<fmt:message key="${labelKey}"/>" class="${styleClass}" onclick="${javascript}" title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>"><img class="${iconClass}" src="${icon}" title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" alt="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" width="${iconWidth}" height="${iconHeight}" /><c:if test="${showTextOfButton}">&nbsp;<fmt:message key="${labelKey}" /></c:if></button></c:when>
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
			command: function (evt) {
				vulpe.util.get("${elementId}").click();
				return false;
			}
		});
	});
	</script>
	</c:if>
</c:if>