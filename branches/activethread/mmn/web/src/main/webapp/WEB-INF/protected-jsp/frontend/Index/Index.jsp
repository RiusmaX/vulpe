<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:if test="${not empty now['memberPersonalReports']}">
<div style="display: table">
<c:forEach var="year" items="${now['memberPersonalReportsYears']}" varStatus="status">
<c:set var="memberPersonalReports" value="${now['memberPersonalReports'][year]}"/>
<script type="text/javascript">
	$(document).ready(function() {
		$('#personalReport-pie${status.index}').highcharts({
			chart: {
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			title: {
				text: 'Ano de Servi\u00E7o de ${year}'
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
						enabled: true,
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
			       <c:forEach var="memberPersonalReport" items="${memberPersonalReports}" varStatus="statusx">
			       <c:if test="${statusx.index > 0}">,</c:if>
			       	<c:choose>
			       	<c:when test="${now.currentMonth == memberPersonalReport.ordinalMonth && year == now['currentYear']}">
			       	{
						name: '${memberPersonalReport.monthI18n}/${memberPersonalReport.year}',
						y: ${memberPersonalReport.totalMinutes/60},
						sliced: true,
						selected: true
					}
					</c:when>
					<c:otherwise>
					['${memberPersonalReport.monthI18n}/${memberPersonalReport.year}', ${memberPersonalReport.totalMinutes/60}]
					</c:otherwise>
					</c:choose>
			       </c:forEach>
				]
			}]
		});
	});
</script>
<div id="personalReport-pie${status.index}" style="width: 500px; height: 320px; display: inline-table;"></div>
</c:forEach>
</div>
</c:if>