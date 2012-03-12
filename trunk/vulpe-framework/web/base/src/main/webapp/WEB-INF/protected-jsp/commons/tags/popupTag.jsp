<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${render}">
	<c:if test="${not empty popupExpressions}"><c:set var="popupExpressions" value="${fn:trim(popupExpressions)}"/></c:if>
	<c:if test="${not empty paramExpressions}"><c:set var="paramExpressions" value="${fn:trim(paramExpressions)}"/></c:if>
	<c:if test="${empty elementId}"><c:set var="elementId" value="${labelKey}"/></c:if>
	<c:if test="${empty popupId}">
		<c:choose>
			<c:when test="${fn:contains(action, '?')}"><c:set var="popupId" value="${util:clearChars(fn:substring(fn:trim(action), 0, fn:indexOf(fn:trim(action), '?')), '/.')}-popup"/></c:when>
			<c:otherwise><c:set var="popupId" value="${util:clearChars(fn:trim(action), '/.')}-popup"/></c:otherwise>
		</c:choose>
	</c:if>
	<c:if test="${empty popupTargetName}">
		<c:if test="${empty targetConfig}"><c:set var="popupTargetName" value="${not empty now['targetName'] ? now['targetName'] : 'entity'}_"/></c:if>
		<c:if test="${not empty targetConfig}">
			<c:set var="popupTargetNameEL" value="${'${'}${targetConfig.baseName}_status.index${'}'}"/>
			<c:set var="popupTargetName" value="${fn:replace(targetConfigPropertyName, '.', '_')}__${util:eval(pageContext, popupTargetNameEL)}__"/>
		</c:if>
	</c:if>
	<c:choose>
		<c:when test="${popupTargetName eq 'false'}"><c:set var="popupTargetName" value=""/></c:when>
		<c:otherwise><c:set var="popupTargetName" value="${popupTargetName}"/></c:otherwise>
	</c:choose>
	<c:if test="${empty paramTargetName}">
		<c:if test="${empty targetConfig}"><c:set var="paramTargetName" value="${not empty now['targetName'] ? now['targetName'] : 'entity'}"/></c:if>
		<c:if test="${not empty targetConfig}">
			<c:set var="paramTargetNameEL" value="${'${'}${targetConfig.baseName}_status.index${'}'}"/>
			<c:set var="paramTargetName" value="${fn:replace(targetConfigPropertyName, '.', '_')}__${util:eval(pageContext, paramTargetNameEL)}__"/>
		</c:if>
	</c:if>
	<c:choose>
		<c:when test="${paramTargetName eq 'false'}"><c:set var="paramTargetName" value=""/></c:when>
		<c:otherwise><c:set var="paramTargetName" value="${paramTargetName}"/></c:otherwise>
	</c:choose>
	<c:if test="${empty popupLayerParent}">
		<c:if test="${empty targetConfig}"><c:set var="popupLayerParent" value="${vulpeFormName}"/></c:if>
		<c:if test="${not empty targetConfig}">
			<c:set var="index" value=""/>
			<c:if test="${not empty targetConfig.parentDetailConfig}">
				<c:set var="indexEL" value="${'${'}${targetConfig.parentDetailConfig.baseName}-status.index${'}'}"/>
				<c:set var="index" value="__${util:eval(pageContext, indexEL)}__"/>
			</c:if>
			<c:set var="popupLayerParent" value="${targetConfig.baseName}${index}"/>
		</c:if>
	</c:if>
	<c:if test="${popupLayerParent eq 'false'}"><c:set var="popupLayerParent" value=""/></c:if>
	<c:if test="${empty paramLayerParent}">
		<c:if test="${empty targetConfig}"><c:set var="paramLayerParent" value="${vulpeFormName}"/></c:if>
		<c:if test="${not empty targetConfig}">
			<c:set var="index" value=""/>
			<c:if test="${not empty targetConfig.parentDetailConfig}">
				<c:set var="indexEL" value="${'${'}${targetConfig.parentDetailConfig.baseName}_status.index${'}'}"/>
				<c:set var="index" value="__${util:eval(pageContext, indexEL)}__"/>
			</c:if>
			<c:set var="paramLayerParent" value="${targetConfig.baseName}${index}"/>
		</c:if>
	</c:if>
	<c:if test="${paramLayerParent eq 'false'}"><c:set var="paramLayerParent" value=""/></c:if>
	<c:set var="popupPropertiesAux" value=""/>
	<c:if test="${not empty popupProperties}">
		<c:forEach items="${fn:split(fn:trim(popupProperties), ',')}" var="prop" varStatus="sProp">
			<c:set var="propResultName" value="${fn:replace(prop, '.', '_')}"/>
			<c:set var="propName" value="${prop}"/>
			<c:forEach items="${fn:split(prop, '=')}" var="propCfg" varStatus="sPropCfg">
				<c:if test="${sPropCfg.first}"><c:set var="propResultName" value="${fn:replace(propCfg, '.', '_')}"/></c:if>
				<c:if test="${sPropCfg.last}"><c:set var="propName" value="${propCfg}"/></c:if>
			</c:forEach>
			<c:choose>
				<c:when test="${sProp.first}"><c:set var="popupPropertiesAux" value="${popupTargetName}${propResultName}=${propName}"/></c:when>
				<c:otherwise><c:set var="popupPropertiesAux" value="${popupPropertiesAux},${popupTargetName}${propResultName}=${propName}"/></c:otherwise>
			</c:choose>
		</c:forEach>
	</c:if>
	<c:set var="popupPropertiesAux" value="${fn:replace(popupPropertiesAux, '[', '__')}"/>
	<c:set var="popupPropertiesAux" value="${fn:replace(popupPropertiesAux, '].', '__')}"/>
	<c:set var="popupPropertiesAux" value="${fn:replace(popupPropertiesAux, ']', '_')}"/>
	<c:set var="popupPropertiesAux" value="${fn:replace(popupPropertiesAux, '.', '_')}"/>
	<c:set var="paramPropertiesAux" value=""/>
	<c:if test="${not empty paramProperties}">
		<c:forEach items="${fn:split(fn:trim(paramProperties), ',')}" var="prop" varStatus="sProp">
			<c:set var="propInputName" value="${prop}"/>
			<c:set var="propName" value="${prop}"/>
			<c:forEach items="${fn:split(prop, '=')}" var="propCfg" varStatus="sPropCfg">
				<c:if test="${sPropCfg.first}"><c:set var="propName" value="${propCfg}"/></c:if>
				<c:if test="${sPropCfg.last}"><c:set var="propInputName" value="${propCfg}"/></c:if>
			</c:forEach>
			<c:choose>
				<c:when test="${sProp.first}"><c:set var="paramPropertiesAux" value="${propName}=${paramTargetName}${propInputName}"/></c:when>
				<c:otherwise><c:set var="paramPropertiesAux" value="${paramPropertiesAux},${propName}=${paramTargetName}${propInputName}"/></c:otherwise>
			</c:choose>
		</c:forEach>
	</c:if>
	<c:if test="${not empty requiredParamProperties}">
		<c:set var="requiredParamPropertiesAux" value=""/>
		<c:forEach items="${fn:split(fn:trim(requiredParamProperties), ',')}" var="prop" varStatus="sProp">
			<c:set var="propInputName" value="${prop}"/>
			<c:set var="propName" value="${prop}"/>
			<c:forEach items="${fn:split(prop, '=')}" var="propCfg" varStatus="sPropCfg">
				<c:if test="${sPropCfg.first}"><c:set var="propName" value="${propCfg}"/></c:if>
				<c:if test="${sPropCfg.last}"><c:set var="propInputName" value="${propCfg}"/></c:if>
			</c:forEach>
			<c:choose>
				<c:when test="${sProp.first}"><c:set var="requiredParamPropertiesAux" value="${propName}=${paramTargetName}${propInputName}"/></c:when>
				<c:otherwise><c:set var="requiredParamPropertiesAux" value="${requiredParamPropertiesAux},${propName}=${paramTargetName}${propInputName}"/></c:otherwise>
			</c:choose>
			<c:if test="${not empty popupProperties || not empty popupExpressions}">
			<script type="text/javascript">
				jQuery(document).ready(function() {
					vulpe.util.get(vulpe.util.decode('${util:urlEncode(paramTargetName)}${util:urlEncode(propInputName)}'), vulpe.util.get(vulpe.util.decode('${util:urlEncode(paramLayerParent)}')) ).change(function(){
					<c:if test="${not empty popupProperties}">
					<c:forEach items="${fn:split(popupProperties, ',')}" var="prop" varStatus="sProp">
						<c:set var="propName" value="${fn:substring(prop, 0, fn:indexOf(prop, '='))}"/>
						vulpe.util.get(vulpe.util.decode('${util:urlEncode(popupTargetName)}${util:urlEncode(propName)}')).val('');
					</c:forEach>
					</c:if>
					<c:if test="${not empty popupExpressions}">
					<c:forEach items="${fn:split(popupExpressions, ',')}" var="prop" varStatus="sProp">
						<c:set var="expName" value="${fn:substring(prop, 0, fn:indexOf(prop, '='))}"/>
						jQuery('${expName}', vulpe.util.get(vulpe.util.decode('${util:urlEncode(popupLayerParent)}'))).val('');
					</c:forEach>
					</c:if>
					});
				});
			</script>
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test="${not empty queryString}"><c:set var="queryString" value="${queryString}&now.popupKey=${popupId}"/></c:if>
	<c:if test="${empty queryString}"><c:set var="queryString" value="now.popupKey=${popupId}"/></c:if>
	<c:if test="${empty popupWidth}"><c:set var="popupWidth" value="450px"/></c:if>
	<c:if test="${empty icon}"><c:set var="icon" value="search"/></c:if>
	<c:if test="${icon eq 'false'}"><c:set var="icon" value=""/></c:if>
	<v:action showOnlyIfAuthenticated="${showOnlyIfAuthenticated}" roles="${roles}" elementId="${elementId}" icon="${icon}" labelKey="${labelKey}" javascript="vulpe.view.request.submitPopup({url: '${action}', queryString: '${queryString}', name: '${popupId}', layerParent: '${popupLayerParent}', paramLayerParent: '${paramLayerParent}', properties: '${popupPropertiesAux}', expressions: '${popupExpressions}', paramProperties: '${paramPropertiesAux}', paramExpressions: '${paramExpressions}', requiredParamProperties: '${requiredParamPropertiesAux}', requiredParamExpressions: '${requiredParamExpressions}', styleClass: '${styleClass}', beforeJs: '${util:urlEncode(beforeJs)}', afterJs: '${util:urlEncode(afterJs)}', width: '${popupWidth}'});" iconWidth="16" iconHeight="16"/>
</c:if>