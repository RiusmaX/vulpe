<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:if test="${not empty now['memberPersonalReports']}">
<script type="text/javascript">
	var pieChart;
	var dunutChart;
	$(document).ready(function() {
		pieChart = new Highcharts.Chart({
			chart: {
				renderTo: 'personalReport-pie',
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			title: {
				text: 'Ano de Serviço de ${now['serviceYear']}'
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
			       <c:forEach var="memberPersonalReport" items="${now['memberPersonalReports']}" varStatus="status">
			       <c:if test="${status.index > 0}">,</c:if>
			       	<c:choose>
			       	<c:when test="${now.currentMonth == memberPersonalReport.ordinalMonth}">
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
<div id="personalReport-pie" style="width: 500px; height: 320px;"></div>
</c:if>