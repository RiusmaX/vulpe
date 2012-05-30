<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:set var="scope" scope="request" value="${scope}"/>
<c:if test="${render}">
	<c:if test="${empty show}"><c:set var="show" value="${true}"/></c:if>
	<c:if test="${!show}"><c:set var="style" value="display:none;${style}"/></c:if>
	<c:if test="${empty sortPropertyInfo}"><c:set var="sortPropertyInfo" value="entitySelect_orderBy"/></c:if>
	<c:if test="${empty renderId}"><c:set var="renderId" value="${true}" scope="request"/></c:if>
	<c:if test="${empty emptyKey}"><c:set var="emptyKey" value="vulpe.message.empty.list"/></c:if>
	<c:set var="name" value=""/>
	<c:set var="baseName" value=""/>
	<c:set var="itemName" value=""/>
	<c:if test="${empty pagingList && (empty detailConfig || now['controllerType'] == 'TABULAR') && empty items}">
		<c:set var="pagingList" value="${(not empty paging && paging.list ne null ? paging : null)}" />
		<c:if test="${empty pagingList}"><c:set var="detailConfig" value="${targetConfig}"/></c:if>
		<c:if test="${not empty pagingList}">
			<c:set var="name" value="entities"/>
			<c:if test="${now['controllerType'] == 'TABULAR'}"><c:set var="detailConfig" value="${targetConfig}"/></c:if>
		</c:if>
		<c:if test="${empty detailConfig}">
			<c:set var="items" value="${entities}"/>
			<c:if test="${not empty items}"><c:set var="name" value="entities"/></c:if>
		</c:if>
	</c:if>
	<c:if test="${not empty targetConfig}"><c:set var="detailConfig" value="${targetConfig}"/></c:if>
	<c:set var="isSelectTableTag" value="${false}" scope="request"/>
	<c:set var="selectCheckOn" scope="request" value=""/>
	<c:set var="selectCheckOff" scope="request" value=""/>
	<c:if test="${not empty pagingList || not empty items}">
		<c:set var="isSelectTableTag" value="${true}" scope="request"/>
		<c:if test="${!exported}">
		<c:set var="selectCheckOn" scope="request" value="vulpe.view.setSelectCheckbox(true);"/>
		<c:set var="selectCheckOff" scope="request" value="vulpe.view.setSelectCheckbox(false);"/>
		</c:if>
		<c:set var="sortPropertyInfoTableTag" value="${sortPropertyInfo}" scope="request"/>
	</c:if>
	<c:if test="${not empty detailConfig}">
		<c:set var="name" value="${targetConfigPropertyName}"/>
		<c:set var="itemName" value="${detailConfig.baseName}_item"/>
		<c:set var="baseName" value="${detailConfig.baseName}"/>
		<c:choose>
		<c:when test="${empty detailConfig.parentDetailConfig}">
			<c:set var="itemsEL" value="${'${'}${targetConfigPropertyName}${'}'}"/>
			<c:set var="items" value="${util:eval(pageContext, itemsEL)}"/>
		</c:when>
		<c:otherwise>
			<c:set var="itemsEL" value="${'${'}${detailConfig.parentDetailConfig.baseName}_item.${detailConfig.propertyName}${'}'}"/>
			<c:set var="items" value="${util:eval(pageContext, itemsEL)}"/>
		</c:otherwise>
		</c:choose>
	</c:if>
	<c:if test="${not empty name && empty elementId}"><c:set var="elementId" value="${name}"/></c:if>
	<c:if test="${not empty elementId && empty name}"><c:set var="name" value="${elementId}"/></c:if>
	<c:if test="${empty itemName}">
		<c:choose>
			<c:when test="${not empty name}">
				<c:set var="itemName" value="${util:clearChars(name, '[].0123456789-,')}_item"/>
				<c:if test="${empty baseName}"><c:set var="baseName" value="${util:clearChars(name, '[].0123456789-,')}"/></c:if>
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
		<c:if test="${empty pagingActionName}"><c:set var="pagingActionName" value="${now['controllerConfig'].ownerController}/read/page"/></c:if>
		<c:if test="${empty pagingFormName}"><c:set var="pagingFormName" value="${vulpeFormName}"/></c:if>
		<c:if test="${empty pagingLayerFields}"><c:set var="pagingLayerFields" value="vulpeSelectForm"/></c:if>
		<c:if test="${empty pagingLayer}"><c:set var="pagingLayer" value="${now['controllerType'] == 'TABULAR' ? '' : 'vulpeSelectTable'}"/></c:if>
	</c:if>
	<c:if test="${not empty detailConfig && empty targetConfig.parentDetailConfig}">
		<c:set var="detailPagingList" value="${detailConfig.name}_pagingList"/>
		<c:set var="detailPagingListEL" value="${'${'}ever['${detailPagingList}']${'}'}"/>
		<c:set var="detailPagingList" value="${util:eval(pageContext, detailPagingListEL)}"/>
		<c:if test="${not empty detailPagingList}">
			<c:set var="pagingList" value="${detailPagingList}"/>
			<c:if test="${empty pagingActionName}"><c:set var="pagingActionName" value="${now['controllerConfig'].ownerController}/detail/${detailConfig.name}/page"/></c:if>
			<c:if test="${empty pagingFormName}"><c:set var="pagingFormName" value="${vulpeFormName}"/></c:if>
			<c:if test="${empty pagingLayerFields}"><c:set var="pagingLayerFields" value="vulpeDetailBody-${detailConfig.name}"/></c:if>
			<c:if test="${empty pagingLayer}"><c:set var="pagingLayer" value="vulpeDetailBody-${detailConfig.name}"/></c:if>
			<c:set var="items" value="${pagingList.list}"/>
		</c:if>
	</c:if>
	<c:if test="${(empty items || fn:length(items) eq 0) && now['controllerType'] == 'MAIN'}"><p><fmt:message key="${emptyKey}"/></p></c:if>
	<c:if test="${not empty items && fn:length(items) > 0}">
		<c:if test="${empty width}"><c:set var="width" value="100%"/></c:if>
		<c:if test="${empty border}"><c:set var="border" value="0"/></c:if>
		<c:if test="${empty cellspacing}"><c:set var="cellspacing" value="0"/></c:if>
		<c:set var="currentTableElementId" value="${elementId}" scope="request"/>
		<table id="${elementId}" width="${width}" border="${border}" cellspacing="${cellspacing}" class="vulpeEntities">
		<thead>
			<c:set var="isHeaderTableTag" value="${true}" scope="request"/>
			<c:if test="${not empty tableHeader}"><tr class="vulpeTableHeader">${tableHeader}</tr></c:if>
			<jsp:invoke fragment="tableBody"/>
		</thead>
		<c:if test="${not empty tableFooter}">
			<tfoot>
				<tr class="vulpeTableFooter">${tableFooter}</tr>
			</tfoot>
		</c:if>
		<c:if test="${name == 'entities' && (empty now['hooks'] || now['hooks'])}"><c:set var="enableHooks" value="${true}" scope="request"/></c:if>
		<tbody>
		<c:forEach var="item" items="${items}" varStatus="status">
			<c:set var="isHeaderTableTag" value="${false}" scope="request"/>
			<c:set var="statusTableTag" value="${baseName}_status" scope="request"/>
			<c:set var="v_status" value="${util:put(pageContext, statusTableTag, status, applicationScope['REQUEST_SCOPE'])}"/>
			<c:set var="currentStatus" value="${status}" scope="request"/>
			<c:set var="itemTableTag" value="${itemName}" scope="request"/>
			<c:set var="v_item" value="${util:put(pageContext, itemTableTag, item, applicationScope['REQUEST_SCOPE'])}"/>
			<c:set var="currentItem" value="${item}" scope="request"/>
			<c:set var="currentDetailConfig" value="${detailConfig}" scope="request"/>
			<jsp:invoke fragment="tableBody"/>
			<c:if test="${not empty detailConfig && not empty detailConfig.subDetails && fn:length(detailConfig.subDetails) > 0}">
				<c:set var="targetConfigPropertyNameLocal" value="${targetConfigPropertyName}"/>
				<c:forEach var="subDetail" items="${detailConfig.subDetails}">
					<c:set var="targetConfig" value="${subDetail}" scope="request"/>
					<c:set var="targetConfigPropertyName" value="${targetConfigPropertyName}[${status.index}].${subDetail.propertyName}" scope="request"/>
					<jsp:include page="/WEB-INF/protected-jsp/commons/detail.jsp">
						<jsp:param name="detailViewPath" value="${subDetail.viewPath}"/>
					</jsp:include>
					<c:set var="targetConfigPropertyName" value="${targetConfigPropertyNameLocal}" scope="request"/>
				</c:forEach>
				<c:set var="targetConfig" value="${detailConfig}" scope="request"/>
				<c:set var="targetConfigPropertyName" value="${targetConfigPropertyNameLocal}" scope="request"/>
			</c:if>
			<c:remove var="statusTableTag" scope="request"/>
			<c:remove var="itemTableTag" scope="request"/>
		</c:forEach>
		</tbody>
		</table>
		<c:remove var="enableHooks" scope="request"/>
		<c:if test="${!exported}"><v:paging list="${pagingList}" actionName="${pagingActionName}" formName="${pagingFormName}" layer="${pagingLayer}" layerFields="${pagingLayerFields}" beforeJs="${pagingBeforeJs}" afterJs="${pagingAfterJs}"/></c:if>
	</c:if>
	<c:if test="${not empty sortPropertyInfoTableTag}">
		<script type="text/javascript">
			$(document).ready(function() {
				vulpe.view.setupSortTable('${sortPropertyInfoTableTag}');
			});
		</script>
	</c:if>
	<c:remove var="targetConfig" scope="request"/>
	<c:remove var="targetConfigPropertyName" scope="request"/>
	<c:remove var="sortPropertyInfoTableTag" scope="request"/>
	<c:remove var="isHeaderTableTag" scope="request"/>
</c:if>