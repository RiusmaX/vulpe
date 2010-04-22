<%@include file="/WEB-INF/protected-jsp/common/common.jsp"%>
<div id="bottom">
<ul>
	<li>
	<h1><fmt:message key='label.site.menu.Learn' /></h1>
	</li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/Learn/frontend/ajax.action');"><fmt:message key='label.site.bottom.first.steps'/></a></li>
	<li>Tutorial</li>
</ul>
<ul>
	<li>
	<h1><fmt:message key='label.site.menu.Community' /></h1>
	</li>
	<li><a href="http://twitter.com/vulpeframework" target="_blank">@vulpeframework</a></li>
	<li><a href="http://groups.google.com/group/vulpe-framework"
		target="_blank">vulpe @ Google group</a></li>
</ul>
<ul>
	<li>
	<h1><fmt:message key='label.site.menu.Code' /></h1>
	</li>
	<!--<li><a href="https://launchpad.net/vulpe" target="_blank">vulpe @ launchpad</a></li>-->
	<li><a href="http://code.google.com/p/vulpe" target="_blank">vulpe
	@ Google code</a></li>
</ul>
<ul class="last">
	<li>
	<h1><fmt:message key='label.site.menu.Download' /></h1>
	</li>
	<li><fmt:message key='label.site.bottom.coming.soon' /></li>
</ul>
</div>