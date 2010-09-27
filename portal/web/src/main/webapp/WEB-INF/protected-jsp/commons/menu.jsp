<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:forEach var="section" items="${sections}">
	<v:menu elementId="${section.id}" label="${section.name}"/>
</c:forEach>