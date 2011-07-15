<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="now['targetName']" value="entitySelect" scope="request"/>
<c:if test="${now['bodyTwice']}">
<c:set var="vulpeBodySelect" value="${true}" scope="request"/>
<fieldset>
<legend><fmt:message>${fn:replace(now['titleKey'], '.twice', '.select')}</fmt:message></legend>
</c:if>
<div id="vulpeSelect">
	<div id="vulpeSelectActions" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/selectActions.jsp" %>
	</div>
	<div id="vulpeSelectForm">
		<jsp:include page="${now['controllerType'] == 'TWICE' ? now['controllerConfig'].viewSelectPath : now['controllerConfig'].viewPath}" />
	</div>
	<div id="vulpeSelectTable">
		<c:remove var="vulpeBodySelect" scope="request"/>
		<jsp:include page="${now['controllerType'] == 'TWICE' ? now['controllerConfig'].viewSelectItemsPath : now['controllerConfig'].viewItemsPath}" />
		<c:if test="${now['controllerType'] == 'REPORT' && not empty downloadInfo}">
			<v:hidden name="downloadInfo" value="${downloadInfo}" saveInSession="true" expireInSession="true" render="false"/>
			<script type="text/javascript">
				$(document).ready(function() {
					vulpe.view.request.submitReport('${util:linkKey('downloadInfo', '', '')}', 800, 600);
				});
			</script>
		</c:if>
	</div>
	<div id="vulpeSelectFooter"></div>
</div>
<c:if test="${now['bodyTwice']}">
</fieldset>
</c:if>