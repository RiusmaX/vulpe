<%@ attribute name="icon" required="false" rtexprvalue="true"%>
<%@ attribute name="showButtonAsImage" required="false"
	rtexprvalue="true"%>
<%@ attribute name="showButtonIcon" required="false" rtexprvalue="true"%>
<%@ attribute name="showButtonText" required="false" rtexprvalue="true"%>
<%@ attribute name="layer" required="false" rtexprvalue="true"%>
<%@ attribute name="action" required="false" rtexprvalue="true"%>
<%@ attribute name="noSubmitForm" required="false" rtexprvalue="true"
	type="java.lang.Boolean"%>
<%@ attribute name="queryString" required="false" rtexprvalue="true"%>
<%@ attribute name="labelKey" required="true" rtexprvalue="true"%>
<%@ attribute name="helpKey" required="false" rtexprvalue="true"%>
<%@ attribute name="widthIcon" required="false" rtexprvalue="true"%>
<%@ attribute name="heightIcon" required="false" rtexprvalue="true"%>
<%@ attribute name="borderIcon" required="false" rtexprvalue="true"%>
<%@ attribute name="style" required="false" rtexprvalue="true"%>
<%@ attribute name="elementId" required="false" rtexprvalue="true"%>
<%@ attribute name="validate" required="false" rtexprvalue="true"
	type="java.lang.Boolean"%>
<%@ attribute name="beforeJs" required="false" rtexprvalue="true"%>
<%@ attribute name="afterJs" required="false" rtexprvalue="true"%>
<%@ attribute name="javascript" required="false" rtexprvalue="true"%>
<%@ attribute name="layerFields" required="false" rtexprvalue="true"%>
<%@ attribute name="styleClass" required="false" rtexprvalue="true"%>
<%@ attribute name="iconClass" required="false" rtexprvalue="true"%>
<%@ attribute name="role" required="false" rtexprvalue="true"%>
<%@ attribute name="logged" required="false" rtexprvalue="true"
	type="java.lang.Boolean"%>

<%@include file="/WEB-INF/protected-jsp/common/common.jsp"%>

<c:set var="exibe" value="${true}" />
<c:if test="${empty showButtonAsImage}">
	<c:set var="showButtonAsImage" value="${true}" />
</c:if>
<c:if test="${empty showButtonIcon}">
	<c:set var="showButtonIcon" value="${false}" />
</c:if>
<c:if test="${empty showButtonText}">
	<c:set var="showButtonText" value="${false}" />
</c:if>

<c:if
	test="${not empty logged && logged eq true && util:isLogged() eq false}">
	<c:set var="exibe" value="${false}" />
</c:if>
<c:if test="${not empty role && util:isRole(role) eq false}">
	<c:set var="exibe" value="${false}" />
</c:if>

<c:if test="${exibe eq true}">
	<c:if test="${empty layerFields}">
		<c:set var="layerFields" value="${actionConfig.formName}" />
	</c:if>
	<c:if test="${layerFields eq 'false'}">
		<c:set var="layerFields" value="" />
	</c:if>

	<c:if test="${empty validate}">
		<c:set var="validate" value="true" />
	</c:if>

	<c:if test="${empty layer}">
		<c:set var="layer" value="body" />
	</c:if>
	<c:if test="${empty elementId}">
		<c:set var="elementId" value="${labelKey}" />
	</c:if>

	<c:if test="${empty javascript}">
		<c:choose>
			<c:when test="${empty action}">
				<c:set var="javascript"
					value="vulpe.view.request.submitForm('${actionConfig.formName}', '${layerFields}', '${queryString}', '${layer}', ${validate}, '${fn:escapeXml(beforeJs)}', '${fn:escapeXml(afterJs)}', false);" />
			</c:when>
			<c:when test="${!noSubmitForm}">
				<c:set var="javascript"
					value="vulpe.view.request.submitFormAction('${action}', '${actionConfig.formName}', '${layerFields}', '${queryString}', '${layer}', ${validate}, '${fn:escapeXml(beforeJs)}', '${fn:escapeXml(afterJs)}');" />
			</c:when>
			<c:otherwise>
				<c:set var="javascript"
					value="vulpe.view.request.submitPage('${action}', '${queryString}', '${layer}', '${fn:escapeXml(beforeJs)}', '${fn:escapeXml(afterJs)}');" />
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:choose>
		<c:when test="${empty icon}">
			<c:if test="${empty styleClass}">
				<c:set var="styleClass" value="submit" />
			</c:if>
			<input style="${style}" id="${elementId}" type="button"
				value="<fmt:message key="${labelKey}"/>" class="${styleClass}"
				onclick="${javascript}"
				title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" />
		</c:when>
		<c:otherwise>
			<c:if test="${empty widthIcon}">
				<c:set var="widthIcon" value="16" />
			</c:if>
			<c:if test="${empty heightIcon}">
				<c:set var="heightIcon" value="16" />
			</c:if>
			<c:if test="${empty borderIcon}">
				<c:set var="borderIcon" value="0" />
			</c:if>
			<c:if test="${!fn:startsWith(icon, pageContext.request.contextPath)}">
				<c:set var="icon" value="${pageContext.request.contextPath}/${icon}" />
			</c:if>
			<c:choose>
				<c:when test="${showButtonAsImage}">
					<a class="${styleClass}" style="${style}" id="${elementId}"
						href="javascript:void(0);" onclick="${javascript}"> <img
						class="${iconClass}" src="${icon}"
						title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>"
						width="${widthIcon}" height="${heightIcon}" border="${borderIcon}" /><c:if
						test="${showButtonText}">&nbsp;
						<fmt:message key="${labelKey}" />
					</c:if></a>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${showButtonIcon}">
							<button style="${style}" id="${elementId}" type="button"
								value="<fmt:message key="${labelKey}"/>" class="${styleClass}"
								onclick="${javascript}"
								title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>">
							<img class="${iconClass}" src="${icon}"
								title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>"
								width="${widthIcon}" height="${heightIcon}"
								border="${borderIcon}" /> <c:if test="${showButtonText}">&nbsp;
						<fmt:message key="${labelKey}" />
							</c:if></button>
						</c:when>
						<c:otherwise>
							<c:set var="styleClass" value="submit" />
							<input style="${style}" id="${elementId}" type="button"
								value="<fmt:message key="${labelKey}"/>" class="${styleClass}"
								onclick="${javascript}"
								title="<fmt:message key="${not empty helpKey ? helpKey : labelKey}"/>" />
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</c:if>