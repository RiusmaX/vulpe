<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${showSize}">
<c:choose>
	<c:when test="${controllerType=='TABULAR' && (empty paging || empty paging.list)}">${fn:length(entities)}</c:when>
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
	<c:if test="${not empty beforeJs}"><c:set var="beforeJs" value=", beforeJs: '${beforeJs}'"/></c:if>
	<c:if test="${not empty afterJs}"><c:set var="afterJs" value=", afterJs: '${afterJs}'"/></c:if>
	<div id="paging" class="vulpePaging">
	<c:choose>
	<c:when test="${global['pagingButtonStyle'] == 'JQUERY_UI'}">
	<ul id="icons" class="ui-widget ui-helper-clearfix">
	<li class="ui-state-default ui-corner-all${empty list.firstPage ? ' vulpeIconOff' : ''}" title="<fmt:message key='label.vulpe.first'/>" onclick="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.firstPage ? 0 : list.firstPage}'${formName}, layersFields: '${layerFields}'${layer}, beforeJs: '${not empty list.firstPage ? beforeJs : 'false'}',${afterJs'});"><span class="ui-icon ui-icon-seek-first"></span></li>
	<li class="ui-state-default ui-corner-all${empty list.previousPage ? ' vulpeIconOff' : ''}" title="<fmt:message key='label.vulpe.previous'/>" onclick="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.previousPage ? 0 : list.previousPage}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${not empty list.previousPage ? beforeJs : 'false'}'${afterJs}});"><span class="ui-icon ui-icon-seek-prev"></span></li>
	</c:when>
	<c:otherwise>
	<v:action elementId="vulpeButtonPagingFirstPage"
		styleClass="${empty list.firstPage ? 'vulpeIconOff' : ''}"
		labelKey="label.vulpe.first" icon="go-first" iconWidth="16" iconHeight="16" iconExtension="png"
		javascript="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.firstPage ? 0 : list.firstPage}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${not empty list.firstPage ? beforeJs : 'false'}'${afterJs}});" />
	<v:action elementId="vulpeButtonPagingPreviousPage"
		styleClass="${empty list.previousPage ? 'vulpeIconOff' : ''}"
		labelKey="label.vulpe.previous" icon="go-previous" iconWidth="16" iconHeight="16" iconExtension="png"
		javascript="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.previousPage ? 0 : list.previousPage}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${not empty list.previousPage ? beforeJs : 'false'}'${afterJs}});" />
	&nbsp;
	</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${global['project-view-pagingStyle'] == 'NUMERIC'}">
	<c:set var="begin" value="${list.page - 5 <= 0 ? 1 : list.page - 5}"/>
	<c:set var="end" value="${list.page + 5 <= begin + 9 ? begin + 9 : list.page + 4}"/>
	<c:if test="${begin < 0}"><c:set var="begin" value="1"/></c:if>
	<c:if test="${end > list.pages}"><c:set var="end" value="${list.pages}"/></c:if>
	<c:set var="endless" value="${end - 10}"/>
	<c:forEach var="page" begin="${(end > begin && endless < begin && endless > 0) ? end - 9 : begin}" end="${end > list.pages ? list.pages : end}">
		<c:set var="link"
			value="javascript:vulpe.view.request.submitPaging({url: '${actionName}/ajax/${page}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${beforeJs}'${afterJs}});" />
		<c:set var="linkStyle" value="" />
		<c:choose>
			<c:when test="${page == list.page}"><c:set var="linkStyle" value="currentPage" /></c:when>
			<c:otherwise><c:set var="linkStyle" value="" /></c:otherwise>
		</c:choose>
		<c:if test="${global['pagingButtonStyle'] == 'JQUERY_UI'}"><li></c:if>
		<c:choose>
			<c:when test="${list.pages > 1}"><a href="javascript:void(0);" onclick="${link}" class="${linkStyle}">${page}</a></c:when>
			<c:otherwise>${page}</c:otherwise>
		</c:choose>
		<c:if test="${global['pagingButtonStyle'] == 'JQUERY_UI'}"></li></c:if>
	</c:forEach>
	</c:when>
	<c:otherwise><fmt:message key="label.vulpe.page" />&nbsp;${list.page}&nbsp;<fmt:message key="label.vulpe.of" />&nbsp;${list.pages}</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${global['pagingButtonStyle'] == 'JQUERY_UI'}">
	<li class="ui-state-default ui-corner-all${empty list.nextPage ? ' vulpeIconOff' : ''}" title="<fmt:message key='label.vulpe.next'/>" onclick="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.nextPage ? 0 : list.nextPage}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${not empty list.nextPage ? beforeJs : 'false'}'${afterJs}});"><span class="ui-icon ui-icon-seek-next"></span></li>
	<li class="ui-state-default ui-corner-all${empty list.lastPage ? ' vulpeIconOff' : ''}" title="<fmt:message key='label.vulpe.last'/>" onclick="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.lastPage ? 0 : list.lastPage}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${not empty list.lastPage ? beforeJs : 'false'}'${afterJs}});"><span class="ui-icon ui-icon-seek-end"></span></li>
	</ul>
	<script type="text/javascript">
		$('#dialog_link, ul#icons li').hover(
			function() { $(this).addClass('ui-state-hover'); },
			function() { $(this).removeClass('ui-state-hover'); }
		);
	</script>
	</c:when>
	<c:otherwise>
	&nbsp;
	<v:action elementId="vulpeButtonPagingNextPage" styleClass="${empty list.nextPage ? 'vulpeIconOff' : ''}"
		labelKey="label.vulpe.next" icon="go-next" iconWidth="16" iconHeight="16" iconExtension="png"
		javascript="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.nextPage ? 0 : list.nextPage}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${not empty list.nextPage ? beforeJs : 'false'}'${afterJs}});" />
	<v:action elementId="vulpeButtonPagingLastPage" styleClass="${empty list.lastPage ? 'vulpeIconOff' : ''}"
		labelKey="label.vulpe.last" icon="go-last" iconWidth="16" iconHeight="16" iconExtension="png"
		javascript="vulpe.view.request.submitPaging({url: '${actionName}/ajax/${empty list.lastPage ? 0 : list.lastPage}'${formName}, layerFields: '${layerFields}'${layer}, beforeJs: '${not empty list.lastPage ? beforeJs : 'false'}'${afterJs}});" />
	</c:otherwise>
	</c:choose>
	</div>
</c:if>