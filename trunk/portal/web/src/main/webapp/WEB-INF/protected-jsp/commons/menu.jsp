<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:if test="${vulpeCurrentLayout == 'FRONTEND'}">
<c:forEach var="menu" items="${menus}">
	<c:choose>
		<c:when test="${not empty menu.navigateToSection}">
			<v:menu elementId="${menu.id}" label="${menu.name}"
				action="/frontend/Index/section/ajax?sectionId=${menu.navigateToSection.id}" />
		</c:when>
		<c:otherwise>
			<v:menu elementId="${menu.id}" label="${menu.name}" url="${menu.url}" />
		</c:otherwise>
	</c:choose>
</c:forEach>
</c:if>