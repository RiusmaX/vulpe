<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${render}">
	<c:if test="${empty show}"><c:set var="show" value="${true}"/></c:if>
	<c:if test="${!show}"><c:set var="style" value="display:none;${style}"/></c:if>
	<c:if test="${empty sortProperty}"><c:set var="sortProperty" value="${property}"/></c:if>
	<c:if test="${not empty styleClass}"><c:set var="styleClass">class="${styleClass}"</c:set></c:if>
	<c:choose>
	<c:when test="${empty isHeaderTableTag || isHeaderTableTag}">
		<c:if test="${not empty labelKey}"><fmt:message key="${labelKey}" var="label"/></c:if>
		<c:if test="${not empty width}"><c:set var="width">width="${width}" </c:set></c:if>
		<c:if test="${not empty labelAlign}"><c:set var="labelStyle" value="text-align: ${labelAlign};${labelStyle}"/></c:if>
		<c:if test="${sort && not empty sortPropertyInfoTableTag && not empty property && !exported}">
			<c:if test="${empty alias}"><c:set var="alias" value="obj"/></c:if>
			<c:choose>
				<c:when test="${global['project-useDB4O']}">
					<c:set var="elementId">id="${sortPropertyInfoTableTag}_${sortProperty}" </c:set>
					<c:set var="label"><a href="javascript:void(0);" onclick="vulpe.view.sortTable('${vulpeFormName}', '${sortPropertyInfoTableTag}', '${sortProperty}');">${label}</a></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="elementId">id="${sortPropertyInfoTableTag}-${alias}_${sortProperty}" </c:set>
					<c:set var="label"><a href="javascript:void(0);" onclick="vulpe.view.sortTable('${vulpeFormName}', '${sortPropertyInfoTableTag}', '${alias}.${sortProperty}');">${label}</a></c:set>
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${not empty onclick}"><c:set var="onclick">onclick="${onclick}"</c:set></c:if>
		<c:if test="${not empty onmouseover}"><c:set var="onmouseover">onmouseover="${onmouseover}"</c:set></c:if>
		<c:if test="${not empty onmouseout}"><c:set var="onmouseout">onmouseout="${onmouseout}"</c:set></c:if>
		<c:if test="${not empty labelStyle}"><c:set var="labelStyle">style="${labelStyle}"</c:set></c:if>
		<c:if test="${empty styleClass}"><c:set var="styleClass">class="vulpeRowHeader"</c:set></c:if>
		<th ${elementId} ${onclick} ${onmouseover} ${onmouseout} colspan="${colspan}" ${width} scope="col" ${labelStyle} ${styleClass}>${label}<c:if test="${showBodyInHeader}"><jsp:doBody/></c:if></th>
	</c:when>
	<c:otherwise>
		<c:if test="${empty listName}"><c:set var="listName" value="entities"/></c:if>
		<c:if test="${not empty align}"><c:set var="style" value="text-align: ${align};${style}"/></c:if>
		<c:if test="${empty value && not empty property && not empty currentItem}">
			<c:set var="valueEL" value="${'${'}currentItem.${property}${'}'}"/>
			<c:set var="value" value="${util:eval(pageContext, valueEL)}"/>
			<c:set var="propertyValue" value="${util:enumInField(currentItem, property, value)}"/>
			<c:if test="${not empty propertyValue}"><c:set var="value" value="${propertyValue}"/></c:if>
		</c:if>
		<c:if test="${not empty onclick}"><c:set var="onclick">onclick="${onclick}" </c:set></c:if>
		<c:if test="${not empty onmouseover}"><c:set var="onmouseover">onmouseover="${onmouseover}"</c:set></c:if>
		<c:if test="${not empty onmouseout}"><c:set var="onmouseout">onmouseout="${onmouseout}"</c:set></c:if>
		<c:if test="${not empty selectCheckOff && empty onclick}"><c:set var="onclick">onclick="${selectCheckOff}"</c:set></c:if>
		<c:if test="${not empty maxlength && fn:length(value) > maxlength}"><c:set var="onclick">onclick="${selectCheckOn}"</c:set></c:if>
		<c:if test="${not empty colspan}"><c:set var="colspan">colspan="${colspan}"</c:set></c:if>
		<c:if test="${not empty style}"><c:set var="style">style="${style}"</c:set></c:if>
		<c:if test="${empty styleClass}"><c:set var="styleClass">class="vulpeColumn ${xstyleClass}"</c:set></c:if>
		<c:if test="${empty elementId}"><c:set var="elementId" value="${labelKey}_${currentStatus.index}"/></c:if>
		<td id="${elementId}" ${onclick} ${onmouseover} ${onmouseout} ${colspan} ${style} ${styleClass}>
			<c:if test="${not empty value}">
				<c:choose>
					<c:when test="${!isImage}">
						<c:choose>
							<c:when test="${not empty booleanTo}">${util:booleanTo(value, booleanTo)}</c:when>
							<c:when test="${not empty enumType}">${util:enumListInField(enumType, value)}</c:when>
							<c:otherwise>
								<c:choose>
								<c:when test="${not empty maxlength && fn:length(value) > maxlength}">
									<c:set var="fullValue" value="${value}"/>
									<c:if test="${fn:length(value) > maxlength}">
										<c:set var="value" value="${fn:substring(value, 0, maxlength)}..."/>
									</c:if>
									<span id="${elementId}_value">${util:toString(value)}&nbsp;</span><span id="${elementId}_showContent" class="vulpeShowContent"><a href="javascript:void(0);" onclick="vulpe.view.showContent('${elementId}');"><fmt:message key="vulpe.messages.showContent"/></a></span>
									<div id="${elementId}_content" class="vulpeContentOverflow" style="display: none">${fullValue}<div id="${elementId}-closeContent" class="vulpeCloseContentOverflow"><a href="javascript:void(0);" onclick="vulpe.view.hideContent('${elementId}');"><fmt:message key="vulpe.messages.close"/></a></div></div>
								</c:when>
								<c:otherwise>${util:toString(value)}</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<c:if test="${empty imageWidth}"><c:set var="imageWidth" value="50"/></c:if>
						<c:if test="${empty imageHeight}"><c:set var="imageHeight" value="38"/></c:if>
						<c:set var="key" value="${listName}[${currentStatus.index}].${property}"/>
						<img src="${util:linkImage(pageContext, key, 'image/jpeg', '', imageWidth, null)}" width="${imageWidth}" height="${imageHeight}" class="vulpeThumb"/>
					</c:otherwise>
				</c:choose>
			</c:if>
			<jsp:doBody/>
		</td>
	</c:otherwise>
	</c:choose>
</c:if>