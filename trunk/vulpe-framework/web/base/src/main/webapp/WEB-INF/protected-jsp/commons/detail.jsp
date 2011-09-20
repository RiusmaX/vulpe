<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="targetConfigLocal" value="${targetConfig}"/>
<c:set var="targetConfigPropertyNameLocal" value="${targetConfigPropertyName}"/>
<c:set var="index" value=""/>
<c:if test="${not empty targetConfig.parentDetailConfig}">
	<c:set var="indexEL" value="${'${'}${targetConfig.parentDetailConfig.baseName}_status.index${'}'}"/>
	<c:set var="index" value="-${util:eval(pageContext, indexEL)}"/>
	<c:choose>
		<c:when test="${(util:eval(pageContext, indexEL) % 2) == 0}"><tr class="vulpeLineOn"></c:when>
		<c:otherwise><tr class="vulpeLineOff"></c:otherwise>
	</c:choose>
	<td colspan="100">
</c:if>
<div id="vulpeDetail-${targetConfigLocal.baseName}" class="${not empty targetConfig.parentDetailConfig ? 'vulpeSubDetailBody' : 'vulpeDetailBody'}">
<c:if test="${not empty targetConfig.parentDetailConfig || now['controllerConfig'].showInTabs eq false}">
	<c:if test="${!showAsAccordion}"><fieldset></c:if>
	<c:choose>
		<c:when test="${showAsAccordion}">
		<h3 id="vulpeDetail-${targetConfigLocal.baseName}-title"><a href="#" id="vulpeDetail-${targetConfigLocal.baseName}-link"><fmt:message key="${targetConfigLocal.titleKey}"/></a></h3>
		</c:when>
		<c:otherwise><legend><fmt:message key="${targetConfigLocal.titleKey}"/></legend></c:otherwise>
	</c:choose>
		<div>
</c:if>
		<c:if test="${!now['onlyToSee']}">
		<div id="vulpeDetailActions-${targetConfigLocal.baseName}" class="vulpeActions">
			<%@include file="/WEB-INF/protected-jsp/commons/detailActions.jsp" %>
		</div>
		</c:if>
		<div id="vulpeDetailBody-${targetConfigLocal.baseName}">
			<jsp:include page="${param.detailViewPath}" />
			<c:remove var="currentDetailConfig" scope="request"/>
			<c:remove var="currentItem" scope="request"/>
			<c:remove var="currentStatus" scope="request"/>
			<c:set var="targetConfig" value="${targetConfigLocal}" scope="request"/>
			<c:set var="targetConfigPropertyName" value="${targetConfigPropertyNameLocal}" scope="request"/>
		</div>
<c:if test="${not empty targetConfig.parentDetailConfig || now['controllerConfig'].showInTabs eq false}">
		</div>
		<c:if test="${!showAsAccordion}"></fieldset></c:if>
</c:if>
</div>
<c:if test="${not empty targetConfig.parentDetailConfig}">
	</td>
</tr>
</c:if>
<c:if test="${(not empty targetConfig.parentDetailConfig || now['controllerConfig'].showInTabs eq false) && targetConfig.showAsAccordion}">
	<script type="text/javascript">
		$(document).ready(function() {
			var id = 'vulpeDetail-${targetConfigLocal.baseName}';
			vulpe.util.get(id).accordion({
				collapsible: true,
				animated: false,
				active: false
			});
		});
	</script>
</c:if>