<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:choose>
	<c:when test="${now['controllerType'] == 'TABULAR'}">
		<v:text labelKey="label.vulpe.security.Role.select.description" targetName="entitySelect" property="description"
			size="60" maxlength="60" />
	</c:when>
	<c:otherwise>
		<v:text labelKey="label.vulpe.security.Role.select.id" property="id" mask="I" />
		<v:text labelKey="label.vulpe.security.Role.select.name" property="name" size="40" />
		<v:text labelKey="label.vulpe.security.Role.select.description" property="description" size="60" />
	</c:otherwise>
</c:choose>