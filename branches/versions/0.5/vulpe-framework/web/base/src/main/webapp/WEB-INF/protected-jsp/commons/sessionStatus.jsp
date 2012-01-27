<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${util:isAuthenticated(pageContext)}">
<div id="sessionStatus"><fmt:message key="label.vulpe.session.time.to.expire"/><span id="sessionTime"></span></div>
</c:if>