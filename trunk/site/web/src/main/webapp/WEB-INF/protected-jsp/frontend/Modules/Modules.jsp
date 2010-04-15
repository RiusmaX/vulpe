<%@include file="/WEB-INF/protected-jsp/common/common.jsp"%>
<div id="content">
<h2><fmt:message key="label.site.Modules.frontend.h2"/></h2>
</div>
<jsp:include page="../bottom.jsp"/>
<script type="text/javascript">
$(document).ready(function() {
	vulpe.util.get("menuVulpe").removeClass("current");
	vulpe.util.get("menuCode").removeClass("current");
	vulpe.util.get("menuCommunity").removeClass("current");
	vulpe.util.get("menuModules").removeClass("current");
	vulpe.util.get("menuLearn").removeClass("current");
	vulpe.util.get("menuModules").addClass("current");
});
</script>