<%@ attribute name="tableBody" fragment="true" %>
<%@ attribute name="tableHeader" required="false" rtexprvalue="true" %>
<%@ attribute name="tableFooter" required="false" rtexprvalue="true" %>
<%@ attribute name="elementId" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="items" required="false" rtexprvalue="true" type="java.util.Collection" %>
<%@ attribute name="detailConfig" required="false" rtexprvalue="true" type="org.vulpe.controller.commons.VulpeBaseDetailConfig" %>
<%@ attribute name="pagingList" required="false" rtexprvalue="true" type="org.vulpe.commons.beans.Paging" %>
<%@ attribute name="pagingActionName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="pagingFormName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="pagingLayerFields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="pagingLayer" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="pagingBeforeJs" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="pagingAfterJs" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="cellspacing" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="border" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="width" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="emptyKey" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="role" required="false" rtexprvalue="true" %>
<%@ attribute name="logged" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="sortPropertyInfo" required="false" rtexprvalue="true" %>
<%@ attribute name="renderId" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>

<c:set var="scope" scope="request" value="${scope}"/>

<c:set var="exibe" value="${true}"/>
<c:if test="${not empty logged && logged eq true && util:isLogged(pageContext) eq false}">
	<c:set var="exibe" value="${false}"/>
</c:if>
<c:if test="${not empty role && util:isRole(pageContext, role) eq false}">
	<c:set var="exibe" value="${false}"/>
</c:if>

<c:if test="${exibe eq true}">
	<c:if test="${empty sortPropertyInfo}">
		<c:set var="sortPropertyInfo" value="${actionConfig.formName}_entity.orderBy"/>
	</c:if>

	<c:if test="${empty renderId}">
		<c:set var="renderId" value="${true}"/>
	</c:if>

	<!-- tag begin -->
	<c:if test="${empty emptyKey}">
		<c:set var="emptyKey" value="vulpe.msg.empty.list"/>
	</c:if>

	<c:set var="name" value=""/>
	<c:set var="baseName" value=""/>
	<c:set var="itemName" value=""/>
	<c:if test="${empty pagingList && empty detailConfig && empty items}">
		<c:set var="pagingList" value="${(not empty paging && paging.list ne null ? paging : null)}" />
		<c:if test="${empty pagingList}">
			<c:set var="detailConfig" value="${targetConfig}"/>
		</c:if>
		<c:if test="${not empty pagingList}">
			<c:set var="name" value="entities"/>
		</c:if>
		<c:if test="${empty detailConfig}">
			<c:set var="items" value="${entities}"/>
			<c:if test="${not empty items}">
				<c:set var="name" value="entities"/>
			</c:if>
		</c:if>
	</c:if>

	<c:set var="isSelect_table_tag" value="${false}" scope="request"/>
	<c:set var="selectCheckOn" scope="request" value=""/>
	<c:set var="selectCheckOff" scope="request" value=""/>
	<c:if test="${not empty pagingList || not empty items}">
		<c:set var="isSelect_table_tag" value="${true}" scope="request"/>
		<c:set var="selectCheckOn" scope="request" value="vulpe.view.setSelectCheckbox(true);"/>
		<c:set var="selectCheckOff" scope="request" value="vulpe.view.setSelectCheckbox(false);"/>
		<c:set var="sortPropertyInfo_table_tag" value="${sortPropertyInfo}" scope="request"/>
	</c:if>

	<c:if test="${not empty detailConfig}">
		<c:set var="name" value="${targetConfigPropertyName}"/>
		<c:set var="itemName" value="${detailConfig.baseName}_item"/>
		<c:set var="baseName" value="${detailConfig.baseName}"/>
		<%-- if detail, then items equals targetConfigPropertyName --%>
		<c:if test="${empty detailConfig.parentDetailConfig}">
			<c:set var="itemsEL" value="${'${'}${targetConfigPropertyName}${'}'}"/>
			<!-- itemsEL= ${itemsEL}  -->
			<c:set var="items" value="${util:eval(pageContext, itemsEL)}"/>
		</c:if>
		<%-- if subdetail, then items equals detailConfig.parentDetailConfig.baseName --%>
		<c:if test="${not empty detailConfig.parentDetailConfig}">
			<c:set var="itemsEL" value="${'${'}${detailConfig.parentDetailConfig.baseName}_item.${detailConfig.propertyName}${'}'}"/>
			<!-- itemsEL: ${itemsEL}  -->
			<c:set var="items" value="${util:eval(pageContext, itemsEL)}"/>
		</c:if>
	</c:if>

	<c:if test="${not empty name && empty elementId}">
		<c:set var="elementId" value="${name}"/>
	</c:if>

	<c:if test="${not empty elementId && empty name}">
		<c:set var="name" value="${elementId}"/>
	</c:if>

	<c:if test="${empty itemName}">
		<c:choose>
			<c:when test="${not empty name}">
				<c:set var="itemName" value="${util:clearChars(name, '[].0123456789-,')}_item"/>
				<c:if test="${empty baseName}">
					<c:set var="baseName" value="${util:clearChars(name, '[].0123456789-,')}"/>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:set var="elementId" value="items"/>
				<c:set var="name" value="items"/>
				<c:set var="baseName" value="items"/>
				<c:set var="itemName" value="items_item"/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="${not empty pagingList}">
		<c:set var="items" value="${pagingList.list}"/>
		<c:if test="${empty pagingActionName}">
			<c:set var="pagingActionName" value="${actionConfig.primitiveActionName}/read"/>
		</c:if>
		<c:if test="${empty pagingFormName}">
			<c:set var="pagingFormName" value="${actionConfig.formName}"/>
		</c:if>
		<c:if test="${empty pagingLayerFields}">
			<c:set var="pagingLayerFields" value="${pagingFormName}"/>
		</c:if>
		<c:if test="${empty pagingLayer}">
			<c:set var="pagingLayer" value="${pagingFormName}_select_table"/>
		</c:if>
	</c:if>

	<c:if test="${empty items || fn:length(items) eq 0}">
		<p><fmt:message key="${emptyKey}"/></p>
	</c:if>

	<c:if test="${not empty items && fn:length(items) > 0}">
		<c:if test="${empty width}">
			<c:set var="width" value="100%"/>
		</c:if>
		<c:if test="${empty border}">
			<c:set var="border" value="0"/>
		</c:if>
		<c:if test="${empty cellspacing}">
			<c:set var="cellspacing" value="1"/>
		</c:if>

		<table id="${elementId}" width="${width}" border="${border}" cellspacing="${cellspacing}" class="entities">
		<tbody>
		<c:forEach var="item" items="${items}" varStatus="i">
			<!-- detail: ${targetConfigPropertyName} - ${targetConfig} -->
			<c:set var="isHeader_table_tag" value="${false}" scope="request"/>
			<c:set var="status_table_tag" value="${baseName}_status" scope="request"/>
			<c:set var="v_status" value="${util:put(pageContext, status_table_tag, i, applicationScope['REQUEST_SCOPE'])}"/>
			<c:set var="item_table_tag" value="${itemName}" scope="request"/>
			<c:set var="v_item" value="${util:put(pageContext, item_table_tag, item, applicationScope['REQUEST_SCOPE'])}"/>

			<c:if test="${not empty detailConfig && renderId}">
				<v:hidden property="id"/>
			</c:if>

			<jsp:invoke fragment="tableBody"/>

			<c:if test="${not empty detailConfig && not empty detailConfig.subDetails && fn:length(detailConfig.subDetails) > 0}">
				<c:set var="targetConfigPropertyNameLocal" value="${targetConfigPropertyName}"/>
				<c:forEach var="subDetail" items="${detailConfig.subDetails}">
					<!-- sub-detalhe: ${targetConfigPropertyName} - ${targetConfig} -->
					<c:set var="targetConfig" value="${subDetail}" scope="request"/>
					<c:set var="targetConfigPropertyName" value="${targetConfigPropertyName}[${i.index}].${subDetail.propertyName}" scope="request"/>
					<jsp:include page="/WEB-INF/protected-jsp/common/detail.jsp">
						<jsp:param name="detail_viewPath" value="${subDetail.viewPath}"/>
					</jsp:include>
					<c:set var="targetConfigPropertyName" value="${targetConfigPropertyNameLocal}" scope="request"/>
				</c:forEach>
				<c:set var="targetConfig" value="${detailConfig}" scope="request"/>
				<c:set var="targetConfigPropertyName" value="${targetConfigPropertyNameLocal}" scope="request"/>
			</c:if>
			<c:set var="status_table_tag" value="${null}" scope="request"/>
			<c:set var="item_table_tag" value="${null}" scope="request"/>
		</c:forEach>
		</tbody>
		<thead>
			<c:set var="isHeader_table_tag" value="${true}" scope="request"/>
			<c:if test="${not empty tableHeader}">
				<tr>
					${tableHeader}
				</tr>
			</c:if>
			<tr>
				<jsp:invoke fragment="tableBody"/>
			</tr>
		</thead>
		<c:if test="${not empty tableFooter}">
			<tfoot>
				<tr>
					${tableFooter}
				</tr>
			</tfoot>
		</c:if>
		</table>
		<c:if test="${not empty pagingList}">
			<div class="paging">
				<v:action widthIcon="16" heightIcon="16" iconClass="${(empty pagingList.firstPage ? 'iconOff' : '')}" icon="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icons/button-go-first.png" labelKey="vulpe.label.first" javascript="vulpe.view.request.submitPaging(${empty pagingList.firstPage ? 0 : pagingList.firstPage}, '${pagingActionName}/ajax', '${pagingFormName}', '${pagingLayerFields}', '${pagingLayer}', '${not empty pagingList.firstPage ? pagingBeforeJs : 'false'}', '${pagingAfterJs}');"/>
				<v:action widthIcon="16" heightIcon="16" iconClass="${(empty pagingList.previousPage ? 'iconOff' : '')}" icon="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icons/button-go-previous.png" labelKey="vulpe.label.previous" javascript="vulpe.view.request.submitPaging(${empty pagingList.previousPage ? 0 : pagingList.previousPage}, '${pagingActionName}/ajax', '${pagingFormName}', '${pagingLayerFields}', '${pagingLayer}', '${not empty pagingList.previousPage ? pagingBeforeJs : 'false'}', '${pagingAfterJs}');"/>
				<fmt:message key="vulpe.label.page"/>&nbsp;${pagingList.page}&nbsp;<fmt:message key="vulpe.label.of"/>&nbsp;${pagingList.pages}
				<v:action widthIcon="16" heightIcon="16" iconClass="${(empty pagingList.nextPage ? 'iconOff' : '')}" icon="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icons/button-go-next.png" labelKey="vulpe.label.next" javascript="vulpe.view.request.submitPaging(${empty pagingList.nextPage ? 0 : pagingList.nextPage}, '${pagingActionName}/ajax', '${pagingFormName}', '${pagingLayerFields}', '${pagingLayer}', '${not empty pagingList.nextPage ? pagingBeforeJs : 'false'}', '${pagingAfterJs}');"/>
				<v:action widthIcon="16" heightIcon="16" iconClass="${(empty pagingList.lastPage ? 'iconOff' : '')}" icon="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icons/button-go-last.png" labelKey="vulpe.label.last" javascript="vulpe.view.request.submitPaging(${empty pagingList.lastPage ? 0 : pagingList.lastPage}, '${pagingActionName}/ajax', '${pagingFormName}', '${pagingLayerFields}', '${pagingLayer}', '${not empty pagingList.lastPage ? pagingBeforeJs : 'false'}', '${pagingAfterJs}');"/>
			</div>
		</c:if>
	</c:if>
	<c:if test="${not empty sortPropertyInfo_table_tag}">
		<script type="text/javascript">
			$(document).ready(function() {
				vulpe.view.setupSortTable('${sortPropertyInfo_table_tag}');
			});
		</script>
	</c:if>
	<c:set var="targetConfig" value="${null}" scope="request"/>
	<c:set var="targetConfigPropertyName" value="${null}" scope="request"/>
	<c:set var="sortPropertyInfo_table_tag" value="${null}" scope="request"/>
	<c:set var="isHeader_table_tag" value="${null}" scope="request"/>
	<!-- tag end -->
</c:if>