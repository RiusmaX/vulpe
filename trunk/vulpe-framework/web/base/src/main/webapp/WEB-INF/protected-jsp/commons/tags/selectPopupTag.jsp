<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${render}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributesConfig.jsp" %>
	<div id="${elementId}-selectPopup" style="${!show ? 'display:none;' : ''}">
	<c:if test="${empty popupId}">
		<c:set var="popupId" value="${elementId}-popupId"/>
	</c:if>
	<c:remove var="elementId"/>
	<c:set var="popupProperties" value="${property}.${identifier}=${identifier},${property}.${description}=${description}${not empty popupProperties ? ',' : ''}${popupProperties}" />
	<c:if test="${empty showBrowseButton}"><c:set var="showBrowseButton" value="${true}"/></c:if>
	<c:if test="${autocomplete && empty autocompleteMinLength}"><c:set var="autocompleteMinLength" value="3"/></c:if>
	<c:set var="autocompleteAction" value="${fn:replace(action, '/select', '/autocomplete/ajax')}" />
	<c:choose>
		<c:when test="${empty autocompleteProperties}"><c:set var="autocompleteProperties" value="${description}"/></c:when>
		<c:otherwise><c:set var="autocompleteProperties" value="${description},${autocompleteProperties}"/></c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${showIdentifier}">
			<p class="vulpeField">
			<c:set var="name" value="${targetName}.${property}"/>
			<c:set var="prepareName" value="${fn:replace(name, '[', '__')}"/>
			<c:set var="prepareName" value="${fn:replace(prepareName, '].', '__')}"/>
			<c:set var="autocompleteId" value="${prepareName}.${identifier}"/>
			<c:set var="autocompleteId" value="${fn:replace(autocompleteId, '.', '_')}"/>
			<c:set var="autocompleteDescription" value="${prepareName}.${description}"/>
			<c:set var="autocompleteDescription" value="${fn:replace(autocompleteDescription, '.', '_')}"/>
			<c:if test="${not empty afterJs}"><c:set var="autocompleteIdAfterJs">, afterJs: function(){${afterJs}}</c:set></c:if>
			<c:choose><c:when test="${empty autocompleteNotFoundMessage}"><fmt:message key="vulpe.error.record.notFound" var="autocompleteNotFoundMessage"/></c:when><c:otherwise><fmt:message key="${autocompleteNotFoundMessage}" var="autocompleteNotFoundMessage"/></c:otherwise></c:choose>
			<c:set var="autocompleteNotFoundMessage">, notFoundMessage: '${autocompleteNotFoundMessage}'</c:set>
			<c:if test="${empty identifierSize}"><c:set var="identifierSize" value="8"/></c:if>
			<c:if test="${not empty labelKey}"><v:label key="${labelKey}"/></c:if>
			<c:set var="valueIdEL" value="${'${'}targetValue.${property}.${identifier}${'}'}"/>
			<c:set var="valueId" value="${util:eval(pageContext, valueIdEL)}"/>
			<c:set var="valueDescriptionEL" value="${'${'}targetValue.${property}.${description}${'}'}"/>
			<c:set var="valueDescription" value="${util:eval(pageContext, valueDescriptionEL)}"/>
			<c:if test="${not empty valueId && not empty valueDescription}"><script type="text/javascript">vulpe.view.selectPopupCache["${autocompleteId}"] = ["${valueId}", "${valueDescription}"];</script></c:if>
			<c:choose><c:when test="${!showAsText}"><v:text property="${property}.${identifier}" size="${identifierSize}" maxlength="${identifierSize}" mask="INTEGER" paragraph="false" onblur="${readonly?'return false;':''}vulpe.view.request.submitAutocompleteIdentifier({url: '${autocompleteAction}', autocomplete: '${autocompleteProperties}', value: $(this).val(), identifier: '${autocompleteId}', description: '${autocompleteDescription}'${autocompleteNotFoundMessage}${autocompleteIdAfterJs}})" readonly="${readonly}" showRequiredIcon="false" required="${required}" requiredFields="${requiredFields}" showAsText="${showAsText}"/></c:when><c:otherwise><v:hidden property="${property}.${identifier}"/></c:otherwise></c:choose>
			<v:text property="${property}.${description}" readonly="${empty readonly ? !autocomplete : readonly}" size="${size}" showAsText="${showAsText}" autocomplete="${autocompleteProperties}" autocompleteURL="${autocompleteAction}" autocompleteSelect="true" autocompleteMinLength="${autocompleteMinLength}" required="${required}" targetValue="${targetValue}" targetName="${targetName}" autocompleteValueList="${autocompleteValueList}" autocompleteProperties="${autocompleteProperties}" autocompleteCallback="${afterJs}" paragraph="false" requiredFields="${requiredFields}">
				<c:if test="${showBrowseButton && !showAsText && !readonly}"><script type="text/javascript">jQuery(document).ready(function() {vulpe.view.request.registerSelectRowCallback(function (){${afterJs}}, '${popupId}', '${popupId}');});</script><v:popup action="${action}" labelKey="label.vulpe.browse" popupId="${popupId}" popupProperties="${popupProperties}" popupWidth="${popupWidth}" /></c:if>
			</v:text>
			</p>
		</c:when>
		<c:otherwise>
			<v:hidden property="${property}.${identifier}"/>
			<v:text labelKey="${labelKey}" property="${property}.${description}" readonly="${empty readonly ? !autocomplete : readonly}" size="${size}" showAsText="${showAsText}" autocomplete="${autocompleteProperties}" autocompleteURL="${autocompleteAction}" autocompleteSelect="true" autocompleteMinLength="${autocompleteMinLength}" required="${required}" targetValue="${targetValue}" targetName="${targetName}" autocompleteValueList="${autocompleteValueList}" autocompleteProperties="${autocompleteProperties}" autocompleteCallback="${afterJs}" requiredFields="${requiredFields}">
				<c:if test="${showBrowseButton && !showAsText && !readonly}"><script type="text/javascript">jQuery(document).ready(function() {vulpe.view.request.registerSelectRowCallback(function (){${afterJs}}, '${popupId}', '${popupId}');});</script><v:popup action="${action}" labelKey="label.vulpe.browse" popupId="${popupId}" popupProperties="${popupProperties}" popupWidth="${popupWidth}"/></c:if>
			</v:text>
		</c:otherwise>
	</c:choose>
	</div>
</c:if>