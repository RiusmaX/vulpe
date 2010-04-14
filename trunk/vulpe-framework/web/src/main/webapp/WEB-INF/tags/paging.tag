<%@include file="/WEB-INF/protected-jsp/common/common.jsp" %>
<%@ attribute name="showSize" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<c:if test="${showSize}">
${paging.size}
</c:if>