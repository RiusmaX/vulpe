<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<div class="line">
<v:text labelKey="label.vulpe.audit.Occurrence.main.id" property="id" showAsText="true"/>
<v:text labelKey="label.vulpe.audit.Occurrence.main.occurrenceType" property="occurrenceType" showAsText="true"/>
</div>
<div class="line">
<v:text labelKey="label.vulpe.audit.Occurrence.main.entity" property="entity" showAsText="true"/>
<v:text labelKey="label.vulpe.audit.Occurrence.main.primaryKey" property="primaryKey" showAsText="true"/>
</div>
<div class="line">
<v:text labelKey="label.vulpe.audit.Occurrence.main.username" property="username" showAsText="true"/>
<v:date labelKey="label.vulpe.audit.Occurrence.main.dateTime" property="dateTime" showAsText="true"/>
</div>
<fieldset>
	<legend>&nbsp;<fmt:message key="label.vulpe.audit.dataHistory"/>&nbsp;</legend>
	<table>
	<tr>
		<td colspan="2"><strong><fmt:message key="label.vulpe.audit.entity"/>:</strong> ${entity.entity}; <strong><fmt:message key="label.vulpe.audit.id"/>:</strong> ${entity.primaryKey}</td>
	</tr>
	<tr>
		<td width="1%"><strong><fmt:message key="label.vulpe.audit.attribute"/></strong></td>
		<td><strong><fmt:message key="label.vulpe.audit.value"/></strong></td>
	</tr>
	<c:forEach var="attribute" items="${entity.dataHistoryAttributes}" varStatus="status">
	<tr class="${status.count % 2 == 0 ? 'vulpeLineOff' : 'vulpeLineOn'}">
		<td>${attribute.name}</td>
		<td>${attribute.value}</td>
	</tr>
	</c:forEach>
	<c:if test="${not empty childOccurrences}">
	<tr>
		<td></td>
		<td>
			<strong><fmt:message key="label.vulpe.audit.child.entities"/></strong><br/>
			<c:forEach var="child" items="${childOccurrences}" varStatus="statusChildren">
			<a href="javascript:void(0);" onclick="vulpe.view.showHideElement('child-${statusChildren.count}');"><strong>> <fmt:message key="label.vulpe.audit.child.entity"/>:</strong> ${child.entity}; <strong><fmt:message key="label.vulpe.audit.id"/>:</strong> ${child.primaryKey}</a><br/>
			<table width="100%" id="child-${statusChildren.count}">
				<tr>
					<td width="1%"><strong><fmt:message key="label.vulpe.audit.attribute"/></strong></td>
					<td class="texto"><strong><fmt:message key="label.vulpe.audit.value"/></strong></td>
				</tr>
				<c:forEach var="attribute" items="${child.dataHistoryAttributes}" varStatus="status">
				<tr class="${status.count % 2 == 0 ? 'vulpeLineOff' : 'vulpeLineOn'}">
					<td>${attribute.name}</td>
					<td>${attribute.value}</td>
				</tr>
				</c:forEach>
			</table>
			</c:forEach>
		</td>
	</tr>
	</c:if>
	</table>
</fieldset>