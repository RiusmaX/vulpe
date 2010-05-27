<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<div id="content">
<h2><fmt:message key="label.site.Index.frontend.h2"/></h2>
<p><fmt:message key="label.site.Index.frontend.pStart"/></p> 
<ul>
	<li><fmt:message key="label.site.Index.frontend.ul.li1"/></li>
	<li><fmt:message key="label.site.Index.frontend.ul.li2"/></li>
	<li><fmt:message key="label.site.Index.frontend.ul.li3"/></li>
	<li><fmt:message key="label.site.Index.frontend.ul.li4"/></li>
	<li><fmt:message key="label.site.Index.frontend.ul.li5"/></li>
	<li><fmt:message key="label.site.Index.frontend.ul.li6"/></li>
	<li><fmt:message key="label.site.Index.frontend.ul.li7"/></li>
	<li><fmt:message key="label.site.Index.frontend.ul.li8"/></li>
	<li><fmt:message key="label.site.Index.frontend.ul.li9"/></li>
	<li><fmt:message key="label.site.Index.frontend.ul.li10"/>
	<ul>
		<li><fmt:message key="label.site.Index.frontend.ul.li10.ul.li1"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li10.ul.li2"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li10.ul.li3"/></li>
	</ul>
	</li>
	<li><fmt:message key="label.site.Index.frontend.ul.li11"/>
	<ul>
		<li><fmt:message key="label.site.Index.frontend.ul.li11.ul.li1"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li11.ul.li2"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li11.ul.li3"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li11.ul.li4"/></li>
	</ul>
	</li>
	<li><fmt:message key="label.site.Index.frontend.ul.li12"/>
	<ul>
		<li><fmt:message key="label.site.Index.frontend.ul.li12.ul.li1"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li12.ul.li2"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li12.ul.li3"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li12.ul.li4"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li12.ul.li5"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li12.ul.li6"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li12.ul.li7"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li12.ul.li8"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li12.ul.li9"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li12.ul.li10"/></li>
		<li><fmt:message key="label.site.Index.frontend.ul.li12.ul.li11"/></li>
	</ul>
	</li>
</ul>
<p><fmt:message key="label.site.Index.frontend.pEnd"/></p>
</div>
<jsp:include page="../bottom.jsp"/>
<script type="text/javascript">
$(document).ready(function() {
	vulpe.util.get("menuVulpe").removeClass("current");
	vulpe.util.get("menuCode").removeClass("current");
	vulpe.util.get("menuCommunity").removeClass("current");
	vulpe.util.get("menuModules").removeClass("current");
	vulpe.util.get("menuLearn").removeClass("current");
	vulpe.util.get("menuVulpe").addClass("current");
});
</script>