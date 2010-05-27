<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<div id="content">
<h2><fmt:message key="label.site.Learn.frontend.h2"/></h2><br>
<h3><fmt:message key="label.site.Learn.frontend.firstproject.h3"/></h3><br>
<fmt:message key="label.site.Learn.frontend.firstproject.1"/>
<p>
<code>
&lt;repository&gt;<br>
	&lt;id&gt;activethread&lt;/id&gt;<br>
	&lt;name&gt;Active Thread Maven 2 Repository&lt;/name&gt;<br>
	&lt;url&gt;http://repository.activethread.com.br/maven2&lt;/url&gt;<br>
	&lt;snapshots&gt;<br>
		&lt;updatePolicy&gt;interval:60&lt;/updatePolicy&gt;<br> 
	&lt;/snapshots&gt;<br>
&lt;/repository&gt;
</code>
</p>
<fmt:message key="label.site.Learn.frontend.firstproject.2"/>
<fmt:message key="label.site.Learn.frontend.firstproject.3"/><br>
<ul>
	<li><strong>vulpe-struts2-db4o</strong> - <fmt:message key="label.site.Learn.frontend.firstproject.ul.li1"/></li>
	<li><strong>vulpe-struts2-db4o-ejb3</strong> - <fmt:message key="label.site.Learn.frontend.firstproject.ul.li2"/></li>
	<li><strong>vulpe-struts2-jpa</strong> - <fmt:message key="label.site.Learn.frontend.firstproject.ul.li3"/></li>
	<li><strong>vulpe-struts2-jpa-ejb3</strong> - <fmt:message key="label.site.Learn.frontend.firstproject.ul.li4"/></li>
</ul>
<br>
<fmt:message key="label.site.Learn.frontend.firstproject.4"/>
<fmt:message key="label.site.Learn.frontend.firstproject.5"/>
<fmt:message key="label.site.Learn.frontend.firstproject.6"/>
</div>
<jsp:include page="../bottom.jsp"/>
<script type="text/javascript">
$(document).ready(function() {
	vulpe.util.get("menuVulpe").removeClass("current");
	vulpe.util.get("menuCode").removeClass("current");
	vulpe.util.get("menuCommunity").removeClass("current");
	vulpe.util.get("menuModules").removeClass("current");
	vulpe.util.get("menuLearn").removeClass("current");
	vulpe.util.get("menuLearn").addClass("current");
});
</script>