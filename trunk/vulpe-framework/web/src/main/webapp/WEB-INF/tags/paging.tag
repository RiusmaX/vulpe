<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ attribute name="list" required="false" rtexprvalue="true" type="org.vulpe.commons.beans.Paging" %>
<%@ attribute name="actionName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="formName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="layerFields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="layer" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="beforeJs" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="afterJs" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="showSize" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<c:if test="${showSize}">
<c:choose>
<c:when test="${controllerType=='TABULAR' && (empty paging || empty paging.list)}">
${fn:length(entities)}
</c:when>
<c:otherwise>
${paging.size}
</c:otherwise>
</c:choose>
</c:if>

<c:if test="${not empty list}">
	<div id="paging" class="vulpePaging">
		<v:action elementId="pagingFirstPage" styleClass="${(empty list.firstPage ? 'vulpeIconOff' : '')}" showButtonAsImage="true" labelKey="vulpe.label.first" javascript="vulpe.view.request.submitPaging(${empty list.firstPage ? 0 : list.firstPage}, '${actionName}/ajax', '${formName}', '${layerFields}', '${layer}', '${not empty list.firstPage ? beforeJs : 'false'}', '${afterJs}');"/>
		<v:action elementId="pagingPreviousPage" styleClass="${(empty list.previousPage ? 'vulpeIconOff' : '')}" showButtonAsImage="true" labelKey="vulpe.label.previous" javascript="vulpe.view.request.submitPaging(${empty list.previousPage ? 0 : list.previousPage}, '${actionName}/ajax', '${formName}', '${layerFields}', '${layer}', '${not empty list.previousPage ? beforeJs : 'false'}', '${afterJs}');"/>
		<fmt:message key="vulpe.label.page"/>&nbsp;${list.page}&nbsp;<fmt:message key="vulpe.label.of"/>&nbsp;${list.pages}
		<v:action elementId="pagingNextPage" styleClass="${(empty list.nextPage ? 'vulpeIconOff' : '')}" showButtonAsImage="true" labelKey="vulpe.label.next" javascript="vulpe.view.request.submitPaging(${empty list.nextPage ? 0 : list.nextPage}, '${actionName}/ajax', '${formName}', '${layerFields}', '${layer}', '${not empty list.nextPage ? beforeJs : 'false'}', '${afterJs}');"/>
		<v:action elementId="pagingLastPage" styleClass="${(empty list.lastPage ? 'vulpeIconOff' : '')}" showButtonAsImage="true" labelKey="vulpe.label.last" javascript="vulpe.view.request.submitPaging(${empty list.lastPage ? 0 : list.lastPage}, '${actionName}/ajax', '${formName}', '${layerFields}', '${layer}', '${not empty list.lastPage ? beforeJs : 'false'}', '${afterJs}');"/>
	</div>
</c:if>