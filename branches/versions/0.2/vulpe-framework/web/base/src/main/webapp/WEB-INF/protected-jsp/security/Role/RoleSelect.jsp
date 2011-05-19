<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<c:choose>
	<c:when test="${now['controllerType'] == 'TABULAR'}">
		<table>
			<tr>
				<td><v:text
					labelKey="label.vulpe.security.Role.select.description"
					targetName="entitySelect" property="description" size="60"
					maxlength="60" /></td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		<v:text labelKey="label.vulpe.security.Role.select.id" property="id"
			mask="I" />
		<v:text labelKey="label.vulpe.security.Role.select.name"
			property="name" size="40" required="true" />
		<v:text labelKey="label.vulpe.security.Role.select.description"
			property="description" size="60" />
	</c:otherwise>
</c:choose>