<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<div id="vulpeReport">
	<div id="vulpeReportActions" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/reportActions.jsp" %>
	</div>
	<div id="vulpeReportBody">
		<p class="vulpeReportForm">
			<jsp:include page="${now['controllerConfig'].viewPath}" />
		</p>
	</div>
	<div id="vulpeReportTable-${vulpeFormName}">
		<p class="vulpeReportTable">
			<jsp:include page="${now['controllerConfig'].viewItemsPath}" />
			<c:if test="${not empty downloadInfo}">
				<v:hidden name="downloadInfo" value="${downloadInfo}" saveInSession="true" expireInSession="true" render="false"/>
				<script type="text/javascript">
					$(document).ready(function() {
						vulpe.view.request.submitReport('${util:linkKey('downloadInfo', '', '')}', 800, 600);
					});
				</script>
			</c:if>
		</p>
	</div>
</div>