<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="targetConfigLocal" value="${targetConfig}"/>
<c:set var="targetConfigPropertyNameLocal" value="${targetConfigPropertyName}"/>

<c:set var="index" value=""/>
<c:if test="${not empty targetConfig.parentDetailConfig}">
	<c:set var="indexEL" value="${'${'}${targetConfig.parentDetailConfig.baseName}_status.index${'}'}"/>
	<c:set var="index" value="_${util:eval(pageContext, indexEL)}"/>
	<c:set var="currentDetailIndex" value="${util:eval(pageContext, indexEL)}" scope="request"/>

	<c:choose>
		<c:when test="${(util:eval(pageContext, indexEL) % 2) == 0}">
<tr class="vulpeLineOn">
		</c:when>
		<c:otherwise>
<tr class="vulpeLineOff">
		</c:otherwise>
	</c:choose>
	<td colspan="100">
</c:if>

<div id="vulpeDetail_${targetConfigLocal.baseName}${currentDetailIndex}" class="detailBody">
<c:if test="${not empty targetConfig.parentDetailConfig || controllerConfig.detailsInTabs eq false}">
		<h3><a href="#" id="vulpeDetail_${targetConfigLocal.baseName}${currentDetailIndex}_link"><fmt:message key="${targetConfigLocal.titleKey}"/></a></h3>
		<div>
</c:if>
		<c:if test="${!onlyToSee}">
		<div id="vulpeDetailActions_${targetConfigLocal.baseName}${currentDetailIndex}" class="vulpeActions">
			<%@include file="/WEB-INF/protected-jsp/commons/detailActions.jsp" %>
		</div>
		</c:if>
		<div id="vulpeDetailBody_${targetConfigLocal.baseName}${currentDetailIndex}">
			<jsp:include page="${param.detailViewPath}" />
			<c:set var="targetConfig" value="${targetConfigLocal}" scope="request"/>
			<c:set var="targetConfigPropertyName" value="${targetConfigPropertyNameLocal}" scope="request"/>
		</div>
<c:if test="${not empty targetConfig.parentDetailConfig || controllerConfig.detailsInTabs eq false}">
		</div>
</c:if>
</div>

<c:if test="${not empty targetConfig.parentDetailConfig}">
	</td>
</tr>
</c:if>

<c:if test="${not empty targetConfig.parentDetailConfig || controllerConfig.detailsInTabs eq false}">
	<script type="text/javascript">
		$(document).ready(function() {
			var id = 'vulpeDetail_${targetConfigLocal.baseName}${currentDetailIndex}';
			vulpe.util.get(id).accordion({
				collapsible: true,
				animated: false
			});
			vulpe.util.get(id + '_link').click();
		});
	</script>
</c:if>