<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="now['targetConfig']Local" value="${now['targetConfig']}"/>
<c:set var="now['now['targetConfig']PropertyName']Local" value="${now['now['targetConfig']PropertyName']}"/>
<c:set var="index" value=""/>
<c:remove var="currentDetailIndex" scope="request"/>
<c:if test="${not empty now['targetConfig'].parentDetailConfig}">
	<c:set var="indexEL" value="${'${'}${now['targetConfig'].parentDetailConfig.baseName}_status.index${'}'}"/>
	<c:set var="index" value="-${util:eval(pageContext, indexEL)}"/>
	<c:set var="currentDetailIndex" value="${util:eval(pageContext, indexEL)}" scope="request"/>
	<c:choose>
		<c:when test="${(util:eval(pageContext, indexEL) % 2) == 0}"><tr class="vulpeLineOn"></c:when>
		<c:otherwise><tr class="vulpeLineOff"></c:otherwise>
	</c:choose>
	<td colspan="100">
</c:if>
<div id="vulpeDetail-${now['targetConfig']Local.baseName}${currentDetailIndex}" class="${not empty now['targetConfig'].parentDetailConfig ? 'vulpeSubDetailBody' : 'vulpeDetailBody'}">
<c:if test="${not empty now['targetConfig'].parentDetailConfig || now['controllerConfig'].showInTabs eq false}">
	<c:if test="${!showAsAccordion}"><fieldset></c:if>
	<c:choose>
		<c:when test="${showAsAccordion}">
		<h3 id="vulpeDetail-${now['targetConfig']Local.baseName}${currentDetailIndex}-title"><a href="#" id="vulpeDetail-${now['targetConfig']Local.baseName}${currentDetailIndex}-link"><fmt:message key="${now['targetConfig']Local.titleKey}"/></a></h3>
		</c:when>
		<c:otherwise><legend><fmt:message key="${now['targetConfig']Local.titleKey}"/></legend></c:otherwise>
	</c:choose>
		<div>
</c:if>
		<c:if test="${!onlyToSee}">
		<div id="vulpeDetailActions-${now['targetConfig']Local.baseName}${currentDetailIndex}" class="vulpeActions">
			<%@include file="/WEB-INF/protected-jsp/commons/detailActions.jsp" %>
		</div>
		</c:if>
		<div id="vulpeDetailBody-${now['targetConfig']Local.baseName}${currentDetailIndex}">
			<jsp:include page="${param.detailViewPath}" />
			<c:remove var="currentDetailConfig" scope="request"/>
			<c:remove var="currentItem" scope="request"/>
			<c:remove var="currentStatus" scope="request"/>
			<c:set var="now['targetConfig']" value="${now['targetConfig']Local}" scope="request"/>
			<c:set var="now['now['targetConfig']PropertyName']" value="${now['now['targetConfig']PropertyName']Local}" scope="request"/>
		</div>
<c:if test="${not empty now['targetConfig'].parentDetailConfig || now['controllerConfig'].showInTabs eq false}">
		</div>
		<c:if test="${!showAsAccordion}"></fieldset></c:if>
</c:if>
</div>
<c:if test="${not empty now['targetConfig'].parentDetailConfig}">
	</td>
</tr>
</c:if>
<c:if test="${(not empty now['targetConfig'].parentDetailConfig || now['controllerConfig'].showInTabs eq false) && now['targetConfig'].showAsAccordion}">
	<script type="text/javascript">
		$(document).ready(function() {
			var id = 'vulpeDetail-${now['targetConfig']Local.baseName}${currentDetailIndex}';
			vulpe.util.get(id).accordion({
				collapsible: true,
				animated: false,
				active: false
			});
		});
	</script>
</c:if>