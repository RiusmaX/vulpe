<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${not empty vulpeShowMessages || !vulpeShowMessages}">
	<c:set var="vulpeShowMessages" value="true" scope="request" />
	<%@include file="/WEB-INF/protected-jsp/commons/messages/error.jsp"%>
</c:if>
<jsp:include page="${now['controllerType'] == 'TWICE' ? now['controllerConfig'].viewSelectItemsPath : now['controllerConfig'].viewItemsPath}" />
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
	// vulpe map actions - init
	vulpe.config.actions = {
		submit : ${util:getMapJSON(pageContext, 'vulpeActions')},
		menu : ${util:getMapJSON(pageContext, 'vulpeMenuActions')},
		sort : ${util:getMapJSON(pageContext, 'vulpeSortActions')},
		control : ${util:getMapJSON(pageContext, 'vulpeControlActions')}
	}
	vulpe.config.controller.owner = '${now['controllerConfig'].ownerController}';
	vulpe.config.layers.layer = "${now['bodyTwice'] ? 'main' : 'body'}";
	vulpe.view.init();
	// vulpe map actions
});
</script>