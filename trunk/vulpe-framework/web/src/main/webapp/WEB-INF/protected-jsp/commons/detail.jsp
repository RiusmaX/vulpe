<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="targetConfigLocal" value="${targetConfig}"/>
<c:set var="targetConfigPropertyNameLocal" value="${targetConfigPropertyName}"/>

<c:set var="index" value=""/>
<c:if test="${not empty targetConfig.parentDetailConfig}">
	<c:set var="indexEL" value="${'${'}${targetConfig.parentDetailConfig.baseName}_status.index${'}'}"/>
	<c:set var="index" value="_${util:eval(pageContext, indexEL)}_"/>
	
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

<div id="vulpeDetail_${targetConfigLocal.baseName}${index}" class="detailBody">
<%-- if subDetail or don't use tabs, then add accordion --%>
<c:if test="${not empty targetConfig.parentDetailConfig || actionConfig.detailsInTabs eq false}">
	<dl id="vulpeSubDetail_${targetConfigLocal.baseName}${index}">
		<dt><fmt:message key="${actionConfig.titleKey}"/></dt>
		<dd>
</c:if>
		<div id="vulpeDetailActions_${targetConfigLocal.baseName}${index}" class="vulpeActions">
			<%@include file="/WEB-INF/protected-jsp/commons/detailActions.jsp" %>
		</div>
		<div id="vulpeDetailBody_${targetConfigLocal.baseName}${index}">
			<jsp:include page="${param.detailViewPath}" />
			<c:set var="targetConfig" value="${targetConfigLocal}" scope="request"/>
			<c:set var="targetConfigPropertyName" value="${targetConfigPropertyNameLocal}" scope="request"/>
		</div>
<%-- if subDetail or don't use tabs, then add accordion --%>
<c:if test="${not empty targetConfig.parentDetailConfig || actionConfig.detailsInTabs eq false}">
		</dd>
	</dl>
</c:if>
</div>

<c:if test="${not empty targetConfig.parentDetailConfig}">
	</td>
</tr>
</c:if>

<%-- if subDetail or don't use tabs, then add accordion --%>
<c:if test="${not empty targetConfig.parentDetailConfig || actionConfig.detailsInTabs eq false}">
	<script type="text/javascript">
		$(document).ready(function() {
			vulpe.util.get('vulpeSubDetail_${targetConfigLocal.baseName}${index}').accordion({start: 'closed'});
		});
	</script>
</c:if>