<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>

<v:text labelKey="label.vulpe.audit.Occurrence.crud.id" property="id" showAsText="true"/>
<v:text labelKey="label.vulpe.audit.Occurrence.crud.occurrenceType" property="occurrenceType" showAsText="true"/>
<v:text labelKey="label.vulpe.audit.Occurrence.crud.entity" property="entity" showAsText="true"/>
<v:text labelKey="label.vulpe.audit.Occurrence.crud.primaryKey" property="primaryKey" showAsText="true"/>
<v:text labelKey="label.vulpe.audit.Occurrence.crud.username" property="username" showAsText="true"/>
<v:date labelKey="label.vulpe.audit.Occurrence.crud.dateTime" property="dateTime" showAsText="true"/>
<table width="100%" cellspacing="0" cellpadding="2" style="border: 1px solid #eee;">
	<tr>
		<td colspan="2" ><strong><fmt:message key="label.vulpe.audit.dataHistory"/></strong></td>
	</tr>
	<tr>
		<td colspan="2" ><strong><fmt:message key="label.vulpe.audit.entity"/>:</strong> ${entity.entity}; <strong><fmt:message key="label.vulpe.audit.id"/>:</strong> ${entity.primaryKey}</td>
	</tr>
	<tr>
		<td width="1%"><strong><fmt:message key="label.vulpe.audit.attribute"/></strong></td>
		<td class="texto"><strong><fmt:message key="label.vulpe.audit.value"/></strong></td>
	</tr>
	<c:forEach var="attribute" items="${entity.dataHistoryAttributes}" varStatus="status">
	<tr class="${status.count % 2 == 0 ? 'lineOn' : 'lineOff'}">
		<td>${attribute.name}</td>
		<td>${attribute.value}</td>
	</tr>
	</c:forEach>
	<c:if test="${not empty childOccurrences}">
	<tr>
		<td></td>
		<td>
			<strong><fmt:message key="label.vulpe.audit.child.entities"/></strong><br>
			<c:forEach var="child" items="${childOccurrences}" varStatus="statusChildren">
			<a href="javascript:void(0);" onclick="vulpe.view.showHideElement('child_${statusChildren.count}');"><strong>> <fmt:message key="label.vulpe.audit.child.entity"/>:</strong> ${child.entity}; <strong><fmt:message key="label.vulpe.audit.id"/>:</strong> ${child.primaryKey}</a><br>
			<table width="100%" cellspacing="0" cellpadding="2" style="border: 1px solid #eee; display: none" id="child_${statusChildren.count}">
				<tr>
					<td width="1%"><strong><fmt:message key="label.vulpe.audit.attribute"/></strong></td>
					<td class="texto"><strong><fmt:message key="label.vulpe.audit.value"/></strong></td>
				</tr>
				<c:forEach var="attribute" items="${child.dataHistoryAttributes}" varStatus="status">
				<tr class="${status.count % 2 == 0 ? 'lineOn' : 'lineOff'}">
					<td>${attribute.name}</td>
					<td>${attribute.value}</td>
				</tr>
				</c:forEach>
			</table>
			</c:forEach>
		</td>
	</c:if>
	</tr>
</table>