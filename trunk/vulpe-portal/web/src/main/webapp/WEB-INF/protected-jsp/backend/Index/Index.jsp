<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<script type="text/javascript">
	var downloadsPieChart;
	$(document).ready(function() {
		downloadsPieChart = new Highcharts.Chart({
			chart: {
				renderTo: 'downloads-pie',
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			title: {
				text: 'Downloads'
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.point.name +'</b>: '+ Math.round(this.percentage) +' %';
				}
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: false,
						color: '#000000',
						connectorColor: '#000000',
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ Math.round(this.percentage) +' %';
						}
					},
					showInLegend: true
				}
			},
		    series: [{
				type: 'pie',
				name: 'Personal Report',
				data: [
			       <c:forEach var="download" items="${ever['vulpePortalDownloads']}" varStatus="status">
			       <c:if test="${status.index > 0}">,</c:if>
			       	<c:choose>
			       	<c:when test="${false}">
			       	{
						name: '${download.name}',    
						y: ${download.downloads},
						sliced: true,
						selected: true
					}
					</c:when>
					<c:otherwise>
					['${download.name}', ${download.downloads}]
					</c:otherwise>
					</c:choose>
			       </c:forEach>
				]
			}]
		});
	});
</script>
<div id="downloads-pie" style="width: 500px; height: 320px;"></div>
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