<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="viewPath" value="${controllerConfig.viewPath}"/>
<c:set var="vulpeBodyTwice" value="${true}" scope="request"/>
<div id="main">
<c:set var="vulpeBodyTwiceType" value="MAIN" scope="request"/>
<jsp:include page="/WEB-INF/protected-jsp/commons/body.jsp"/>
</div>
<div id="select">
<c:set var="vulpeBodyTwiceType" value="SELECT" scope="request"/>
<jsp:include page="/WEB-INF/protected-jsp/commons/body.jsp"/>
</div>