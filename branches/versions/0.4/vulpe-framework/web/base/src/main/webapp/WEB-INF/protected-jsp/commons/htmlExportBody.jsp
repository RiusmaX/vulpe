<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<div id="container">
	<div id="export">
		<div id="header">
		</div>
		<div id="menu">
		</div>
		<div id="messages" style="display: none;" class="vulpeMessages"></div>
		<div id="body">
			<decorator:body/>
		</div>
	</div>
	<div id="footer">
	</div>
</div>