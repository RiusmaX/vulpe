<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${showSize}">
<c:choose>
	<c:when test="${now['controllerType'] == 'TABULAR' && (empty paging || empty paging.list)}">${fn:length(entities)}</c:when>
	<c:otherwise>${paging.size}</c:otherwise>
</c:choose>
</c:if>
<c:if test="${not empty list}">
	<c:choose>
		<c:when test="${not empty layer && layer != 'body'}"><c:set var="layer" value=", layer: '${layer}'"/></c:when>
		<c:otherwise><c:set var="layer" value=""/></c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${not empty formName && formName != layerFields}"><c:set var="formName" value=", formName: '${formName}'"/></c:when>
		<c:otherwise><c:set var="formName" value=""/></c:otherwise>
	</c:choose>
	<c:if test="${not empty afterJs}"><c:set var="afterJs" value=", afterJs: '${afterJs}'"/></c:if>
	<div id="paging" class="vulpePaging">
	<c:set var="navigateToFirst" value="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.firstPage ? 0 : list.firstPage}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${not empty list.firstPage ? beforeJs : 'false'}'${afterJs}});"/>
	<c:set var="navigateToPrevious" value="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.previousPage ? 0 : list.previousPage}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${not empty list.previousPage ? beforeJs : 'false'}'${afterJs}});"/>
	<c:set var="navigateToNext" value="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.nextPage ? 0 : list.nextPage}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${not empty list.nextPage ? beforeJs : 'false'}'${afterJs}});"/>
	<c:set var="navigateToLast" value="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.lastPage ? 0 : list.lastPage}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${not empty list.lastPage ? beforeJs : 'false'}'${afterJs}});"/>
	<c:choose>
	<c:when test="${global['application-view-paging-buttonStyle'] == 'JQUERY_UI'}">
	<ul id="icons" class="ui-widget ui-helper-clearfix">
	<li id="vulpeButtonPagingFirstPage" class="ui-state-default ui-corner-all${empty list.firstPage ? ' vulpeItemOff' : ''} vulpeActions" title="<fmt:message key='label.vulpe.first'/>"><span class="ui-icon ui-icon-seek-first"></span></li>
	<li id="vulpeButtonPagingPreviousPage" class="ui-state-default ui-corner-all${empty list.previousPage ? ' vulpeItemOff' : ''} vulpeActions" title="<fmt:message key='label.vulpe.previous'/>"><span class="ui-icon ui-icon-seek-prev"></span></li>
	${util:putMap(pageContext, 'vulpeActions', 'vulpeButtonPagingFirstPage', navigateToFirst, true)}
	${util:putMap(pageContext, 'vulpeActions', 'vulpeButtonPagingPreviousPage', navigateToPrevious, true)}
	</c:when>
	<c:otherwise>
	<v:action elementId="vulpeButtonPagingFirstPage"
		disabled="${empty list.firstPage}"
		labelKey="label.vulpe.first" icon="go-first" iconWidth="16" iconHeight="16" iconExtension="png" showButtonAsLink="${global['application-view-paging-showButtonAsLink']}"
		javascript="${navigateToFirst}" />
	<v:action elementId="vulpeButtonPagingPreviousPage"
		disabled="${empty list.previousPage}"
		labelKey="label.vulpe.previous" icon="go-previous" iconWidth="16" iconHeight="16" iconExtension="png" showButtonAsLink="${global['application-view-paging-showButtonAsLink']}"
		javascript="${navigateToPrevious}" />
	&nbsp;
	</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${global['application-view-paging-style'] == 'NUMERIC'}">
	<c:set var="begin" value="${list.page - 5 <= 0 ? 1 : list.page - 5}"/>
	<c:set var="end" value="${list.page + 5 <= begin + 9 ? begin + 9 : list.page + 4}"/>
	<c:if test="${begin < 0}"><c:set var="begin" value="1"/></c:if>
	<c:if test="${end > list.pages}"><c:set var="end" value="${list.pages}"/></c:if>
	<c:set var="endless" value="${end - 10}"/>
	<c:forEach var="page" begin="${(end > begin && endless < begin && endless > 0) ? end - 9 : begin}" end="${end > list.pages ? list.pages : end}" varStatus="status">
		<c:set var="link" value="javascript:vulpe.view.request.submitPaging({url: '${actionName}/ajax/${page}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${beforeJs}'${afterJs}});" />
		<c:set var="linkStyle" value="" />
		<c:choose>
			<c:when test="${page == list.page}"><c:set var="linkStyle" value="currentPage" /></c:when>
			<c:otherwise><c:set var="linkStyle" value="" /></c:otherwise>
		</c:choose>
		<c:if test="${global['application-view-paging-buttonStyle'] == 'JQUERY_UI'}"><li></c:if>
		<c:choose>
			<c:when test="${list.pages > 1}">
			<c:set var="linkId" value="vulpeButtonPaging${status.count}Page" />
			<a id="${linkId}" href="javascript:void(0);" class="${linkStyle} vulpeActions">${page}</a>
			${util:putMap(pageContext, 'vulpeActions', linkId, link, true)}
			</c:when>
			<c:otherwise>${page}</c:otherwise>
		</c:choose>
		<c:if test="${global['application-view-paging-buttonStyle'] == 'JQUERY_UI'}"></li></c:if>
	</c:forEach>
	</c:when>
	<c:otherwise><fmt:message key="label.vulpe.page" />&nbsp;${list.page}&nbsp;<fmt:message key="label.vulpe.of" />&nbsp;${list.pages}</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${global['application-view-paging-buttonStyle'] == 'JQUERY_UI'}">
	<li id="vulpeButtonPagingNextPage" class="ui-state-default ui-corner-all${empty list.nextPage ? ' vulpeItemOff' : ''} vulpeActions" title="<fmt:message key='label.vulpe.next'/>"><span class="ui-icon ui-icon-seek-next"></span></li>
	<li id="vulpeButtonPagingLastPage" class="ui-state-default ui-corner-all${empty list.lastPage ? ' vulpeItemOff' : ''} vulpeActions" title="<fmt:message key='label.vulpe.last'/>"><span class="ui-icon ui-icon-seek-end"></span></li>
	${util:putMap(pageContext, 'vulpeActions', 'vulpeButtonPagingNextPage', navigateToNext, true)}
	${util:putMap(pageContext, 'vulpeActions', 'vulpeButtonPagingLastPage', navigateToLast, true)}
	</ul>
	</c:when>
	<c:otherwise>
	&nbsp;
	<v:action elementId="vulpeButtonPagingNextPage" disabled="${empty list.nextPage}"
		labelKey="label.vulpe.next" icon="go-next" iconWidth="16" iconHeight="16" iconExtension="png" showButtonAsLink="${global['application-view-paging-showButtonAsLink']}"
		javascript="${navigateToNext}" />
	<v:action elementId="vulpeButtonPagingLastPage" disabled="${empty list.lastPage}"
		labelKey="label.vulpe.last" icon="go-last" iconWidth="16" iconHeight="16" iconExtension="png" showButtonAsLink="${global['application-view-paging-showButtonAsLink']}"
		javascript="${navigateToLast}" />
	</c:otherwise>
	</c:choose>
	</div>
</c:if>