<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:choose>
<c:when test="${vulpeCurrentLayout == 'FRONTEND'}">
<c:forEach var="menu" items="${vulpePortalMenus}">
	<c:choose>
		<c:when test="${not empty menu.navigateToSection}">
			<v:menu elementId="${menu.id}" label="${menu.name}"
				action="/frontend/Index/section/ajax?sectionId=${menu.navigateToSection.id}" />
		</c:when>
		<c:otherwise>
			<v:menu elementId="${menu.id}" label="${menu.name}" url="${menu.url}" />
		</c:otherwise>
	</c:choose>
</c:forEach>
</c:when>
<c:otherwise>
	<v:menu elementId="Portal" labelKey="label.portal.menu.Index">
		<v:menu elementId="Portal" labelKey="label.portal.menu.Index.Portal" action="/core/Portal/update"/>
		<v:menu elementId="Portal" labelKey="label.portal.menu.Index.Language" action="/core/Language/tabular"/>
		<v:menu elementId="Portal" labelKey="label.portal.menu.Index.Section" action="/core/Section/tabular"/>
		<v:menu elementId="Portal" labelKey="label.portal.menu.Index.Menu" action="/core/Menu/select"/>
		<v:menu elementId="Portal" labelKey="label.portal.menu.Index.Category" action="/core/Category/tabular"/>
		<v:menu elementId="Portal" labelKey="label.portal.menu.Index.Content" action="/core/Content/select"/>
		<v:menu elementId="Portal" labelKey="label.portal.menu.Index.Download" action="/core/Download/select"/>
		<v:menu elementId="Portal" labelKey="label.portal.menu.Index.Link" action="/core/Link/select"/>
		<v:menu elementId="Portal" labelKey="label.portal.menu.Index.Community" action="/core/Community/select"/>
		<v:menu elementId="Portal" labelKey="label.portal.menu.Index.Social" action="/core/Social/select"/>
	</v:menu>
</c:otherwise>
</c:choose>