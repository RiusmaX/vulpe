<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>
<c:if test="${empty label}">
	<fmt:message key="${labelKey}" var="label"/>
</c:if>
<c:if test="${empty help}">
<c:choose>
	<c:when test="${empty helpKey}">
		<c:choose>
			<c:when test="${not empty labelKey}">
				<c:set var="helpKey" value="${labelKey}"/>
				<fmt:message key="${helpKey}" var="help"/>
			</c:when>
			<c:otherwise>
				<c:set var="help" value="${label}"/>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${not empty hotKey}">
				<c:set var="help" value="${help} [${hotKey}]" />
			</c:when>
			<c:when test="${not empty accesskey}">
				<c:set var="help" value="${help} [Alt+Shift+${accesskey}]" />
			</c:when>
		</c:choose>
	</c:when>
	<c:otherwise>
		<fmt:message key="${helpKey}" var="help"/>
	</c:otherwise>
</c:choose>
</c:if>
<c:if test="${not empty accesskey}">
	<c:set var="accesskey"> accesskey="${accesskey}"</c:set>
</c:if>
<c:if test="${current}">
	<c:set var="currentClass"> class="vulpeCurrentMenu"</c:set>
</c:if>
<c:choose>
<c:when test="${not empty action}">
	<c:if test="${!fn:startsWith(action, '/')}">
		<c:set var="action" value="/${action}"/>
	</c:if>
	<c:if test="${!fn:contains(action, '/ajax')}">
		<c:set var="action" value="${action}/ajax"/>
	</c:if>
	<c:set var="onclick"> onclick="vulpe.view.request.submitLink('${action}');"</c:set>
</c:when>
<c:otherwise>
<c:set var="onclick"> onclick="$(window.location).attr('href', '${url}');"</c:set>
</c:otherwise>
</c:choose>
<c:if test="${show}">
<li id="vulpeMenu-${elementId}">
	<a id="vulpeMenuLink-${elementId}" href="javascript:void(0);"${currentClass}${onclick}${accesskey} title="${help}"><span>${label}</span></a>
	<ul>
		<jsp:doBody/>
	</ul>
	<script type="text/javascript">
	$(document).ready(function() {
		vulpe.util.get("vulpeMenuLink-${elementId}").bind("click", function() {
			jQuery(".vulpeCurrentMenu").removeClass("vulpeCurrentMenu")
			$(this).addClass("vulpeCurrentMenu");
		});
	<c:if test="${not empty hotKey}">
		vulpe.util.addHotKey({
			hotKey: "${hotKey}",
			command: function (evt) {
				vulpe.util.get("vulpeMenuLink-${elementId}").click();
				return false;
			}
		});
	</c:if>
	});
	</script>
</li>
</c:if>