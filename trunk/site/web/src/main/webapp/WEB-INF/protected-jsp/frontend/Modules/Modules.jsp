<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<div id="frontendContent">
<h2><fmt:message key="label.site.frontend.Modules.h2"/></h2><br>
<h3><fmt:message key="label.site.frontend.Modules.h3"/></h3>
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