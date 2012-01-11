<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="show" value="${true}"/>
<c:if test="${global['application-view-layout-showSliderPanelOnlyIfAuthenticated'] && !util:isAuthenticated(pageContext)}">
	<c:set var="show" value="${false}"/>
</c:if>
<c:if test="${show}">
<div id="sliderWrap">
	<span id="sliderClose"></span>
    <div id="slider">
        <div id="sliderContent">
            <%@include file="/WEB-INF/protected-jsp/commons/sliderPanelContent.jsp" %>
        </div>
        <div id="openCloseWrap">
            <a href="javascript:void(0)" id="sliderPanelAction">
                <span class="ui-icon ui-icon-triangle-1-s" style="cursor: pointer"></span>
            </a>
        </div>
    </div>
</div>
</c:if>