<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:choose>
	<c:when test="${vulpeCurrentLayout == 'FRONTEND'}">
		<c:forEach var="menu" items="${vulpePortalMenus}">
			<c:choose>
				<c:when test="${not empty menu.navigateToSection}">
					<v:menu elementId="${menu.id}" label="${menu.name}"
						action="/frontend/Index/section/ajax?sectionId=${menu.navigateToSection.id}"
						current="${not empty menu.selectOnLoad && menu.selectOnLoad}" />
				</c:when>
				<c:otherwise>
					<v:menu elementId="${menu.id}" label="${menu.name}" url="${menu.url}"
						current="${not empty menu.selectOnLoad && menu.selectOnLoad}" />
				</c:otherwise>
			</c:choose>
			<c:if test="${not empty menu.selectOnLoad && menu.selectOnLoad}">
				<script type="text/javascript">
					$(document).ready(function() {
						var menu = vulpe.util.get("vulpeMenuLink-${menu.id}");
						if (menu.length == 1) {
							menu.click();
						}
					});
				</script>
			</c:if>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<v:menu elementId="Management" labelKey="label.portal.menu.Index">
			<v:menu elementId="Portal" labelKey="label.portal.menu.Index.Portal" action="/core/Portal/update" />
			<v:menu elementId="Language" labelKey="label.portal.menu.Index.Language"
				action="/core/Language/tabular" />
			<v:menu elementId="Section" labelKey="label.portal.menu.Index.Section"
				action="/core/Section/tabular" />
			<v:menu elementId="Menu" labelKey="label.portal.menu.Index.Menu" action="/core/Menu/select" />
			<v:menu elementId="Category" labelKey="label.portal.menu.Index.Category"
				action="/core/Category/tabular" />
			<v:menu elementId="Content" labelKey="label.portal.menu.Index.Content"
				action="/core/Content/select" />
			<v:menu elementId="Download" labelKey="label.portal.menu.Index.Download"
				action="/core/Download/select" />
			<v:menu elementId="Link" labelKey="label.portal.menu.Index.Link" action="/core/Link/select" />
			<v:menu elementId="Community" labelKey="label.portal.menu.Index.Community"
				action="/core/Community/select" />
			<v:menu elementId="Social" labelKey="label.portal.menu.Index.Social" action="/core/Social/select" />
		</v:menu>
	</c:otherwise>
</c:choose>