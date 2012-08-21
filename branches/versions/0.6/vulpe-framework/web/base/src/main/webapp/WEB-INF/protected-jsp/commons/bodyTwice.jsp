<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="viewPath" value="${now['controllerConfig'].viewPath}"/>
<c:set var="now['bodyTwice']" value="${true}" scope="request"/>
<div id="main">
<c:set var="now['bodyTwiceType']" value="MAIN" scope="request"/>
<jsp:include page="/WEB-INF/protected-jsp/commons/body.jsp"/>
</div>
<div id="select">
<c:set var="now['bodyTwiceType']" value="SELECT" scope="request"/>
<jsp:include page="/WEB-INF/protected-jsp/commons/body.jsp"/>
</div>