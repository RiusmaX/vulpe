<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<table style="margin: 0; width: 100%">
	<tr>
		<td valign="top" width="33%">
		<table class="vulpeEntities" width="100%" cellpadding="0" cellspacing="0">
			<thead class="vulpeTableHeader">
				<tr>
					<th colspan="2"><fmt:message key='label.portal.Downloads' /></th>
				</tr>
			</thead>
			<tr>
				<th class="vulpeRowHeader">Nome</th>
				<th class="vulpeRowHeader" align="right">Downloads</th>
			</tr>
			<c:forEach var="download" items="${ever['vulpePortalDownloads']}" varStatus="status">
				<tr class="${status.index % 2 == 0 ? 'vulpeLineOn' : 'vulpeLineOff'}">
					<td>${download.name}</td>
					<td align="right">${download.downloads}</td>
				</tr>
			</c:forEach>
		</table>
		</td>
		<td valign="top" width="33%">
		<table class="vulpeEntities" width="100%" cellpadding="0" cellspacing="0">
			<thead class="vulpeTableHeader">
				<tr>
					<th colspan="2"><fmt:message key='label.portal.Contents' /></th>
				</tr>
			</thead>
			<tr>
				<th class="vulpeRowHeader">Nome</th>
				<th class="vulpeRowHeader" align="right">Views</th>
			</tr>
			<c:forEach var="content" items="${ever['vulpePortalContents']}" varStatus="status">
				<tr class="${status.index % 2 == 0 ? 'vulpeLineOn' : 'vulpeLineOff'}">
					<td>${content.title}</td>
					<td align="right">${content.views}</td>
				</tr>
			</c:forEach>
		</table>
		</td>
		<td valign="top" width="33%">
		<table class="vulpeEntities" width="100%" cellpadding="0" cellspacing="0">
			<thead class="vulpeTableHeader">
				<tr>
					<th colspan="2"><fmt:message key='label.portal.Links' /></th>
				</tr>
			</thead>
			<tr>
				<th class="vulpeRowHeader">Nome</th>
				<th class="vulpeRowHeader" align="right">Clicks</th>
			</tr>
			<c:forEach var="link" items="${ever['vulpePortalLinks']}" varStatus="status">
				<tr class="${status.index % 2 == 0 ? 'vulpeLineOn' : 'vulpeLineOff'}">
					<td>${link.name}</td>
					<td align="right">${link.clicks}</td>
				</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
</table>