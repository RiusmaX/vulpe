<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${render}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributesConfig.jsp" %>
	<div id="${elementId}-selectPopup">
	<c:remove var="elementId"/>
	<c:if test="${empty showBrowseButton}"><c:set var="showBrowseButton" value="${true}"/></c:if>
	<c:if test="${autocomplete && empty autocompleteMinLength}"><c:set var="autocompleteMinLength" value="3"/></c:if>
	<c:set var="autocompleteAction" value="${fn:replace(action, '/select', '/autocomplete/ajax')}" />
	<c:if test="${empty popupId}">
		<c:choose>
			<c:when test="${fn:contains(action, '?')}"><c:set var="popupId" value="${util:clearChars(fn:substring(fn:trim(action), 0, fn:indexOf(fn:trim(action), '?')), '/.')}-popup"/></c:when>
			<c:otherwise><c:set var="popupId" value="${util:clearChars(fn:trim(action), '/.')}-popup"/></c:otherwise>
		</c:choose>
	</c:if>
	<c:choose>
		<c:when test="${showIdentifier}">
			<p class="vulpeField">
			<c:set var="name" value="${targetName}.${property}.${description}"/>
			<c:set var="prepareName" value="${fn:replace(name, '[', '__')}"/>
			<c:set var="prepareName" value="${fn:replace(prepareName, '].', '__')}"/>
			<c:set var="autocompleteId" value="${vulpeFormName}-${prepareName}"/>
			<c:set var="autocompleteId" value="${fn:replace(autocompleteId, '.', '_')}"/>
			<c:if test="${not empty afterJs}"><c:set var="autocompleteIdAfterJs">, afterJs: function(){${afterJs}}</c:set></c:if>
			<c:if test="${empty identifierSize}"><c:set var="identifierSize" value="5"/></c:if>
			<c:if test="${not empty labelKey}"><v:label key="${labelKey}"/></c:if>
			<c:set var="valueIdEL" value="${'${'}targetValue.${property}.${identifier}${'}'}"/>
			<c:set var="valueId" value="${util:eval(pageContext, valueIdEL)}"/>
			<c:if test="${not empty valueId}"><script type="text/javascript">vulpe.view.selectPopupIds["${autocompleteId}"] = ${valueId};</script></c:if>
			<c:choose><c:when test="${!showAsText}"><v:text property="${property}.${identifier}" size="${identifierSize}" mask="INTEGER" paragraph="false" onblur="${readonly?'return false;':''}vulpe.view.request.submitAutocompleteIdentifier({url: '${autocompleteAction}', autocomplete: '${description}', value: $(this).val(), id: '${autocompleteId}'${autocompleteIdAfterJs}})" readonly="${readonly}" showRequiredIcon="false" required="${required}"/></c:when><c:otherwise><v:hidden property="${property}.${identifier}"/></c:otherwise></c:choose>
			<v:text property="${property}.${description}" readonly="${empty readonly ? !autocomplete : readonly}" size="${size}" showAsText="${showAsText}" autocomplete="${description}" autocompleteURL="${autocompleteAction}" autocompleteSelect="true" autocompleteMinLength="${autocompleteMinLength}" required="${required}" targetValue="${targetValue}" targetName="${targetName}" autocompleteValueList="${autocompleteValueList}" autocompleteProperties="${autocompleteProperties}" autocompleteCallback="${afterJs}" paragraph="false">
				<c:if test="${showBrowseButton && !showAsText && !readonly}"><script type="text/javascript">jQuery(document).ready(function() {vulpe.view.request.registerSelectRowCallback(function (){${afterJs}}, '${popupId}', '${popupId}');});</script><v:popup action="${action}" labelKey="label.vulpe.browse" popupId="${popupId}" popupProperties="${popupProperties}" popupWidth="${popupWidth}" /></c:if>
			</v:text>
			</p>
		</c:when>
		<c:otherwise>
			<v:hidden property="${property}.${identifier}"/>
			<v:text labelKey="${labelKey}" property="${property}.${description}" readonly="${empty readonly ? !autocomplete : readonly}" size="${size}" showAsText="${showAsText}" autocomplete="${description}" autocompleteURL="${autocompleteAction}" autocompleteSelect="true" autocompleteMinLength="${autocompleteMinLength}" required="${required}" targetValue="${targetValue}" targetName="${targetName}" autocompleteValueList="${autocompleteValueList}" autocompleteProperties="${autocompleteProperties}" autocompleteCallback="${afterJs}">
				<c:if test="${showBrowseButton && !showAsText && !readonly}"><script type="text/javascript">jQuery(document).ready(function() {vulpe.view.request.registerSelectRowCallback(function (){${afterJs}}, '${popupId}', '${popupId}');});</script><v:popup action="${action}" labelKey="label.vulpe.browse" popupId="${popupId}" popupProperties="${popupProperties}" popupWidth="${popupWidth}"/></c:if>
			</v:text>
		</c:otherwise>
	</c:choose>
	</div>
</c:if>