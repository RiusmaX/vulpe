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
		var colors = Highcharts.getOptions().colors,
			categories = [${now['chartYears']}],
			name = 'Years',
			data = [{ 
					y: ${now['initialYearPercent']},
					color: colors[0],
					drilldown: {
						name: 'year1',
						categories: [<c:forEach var="memberPersonalReport" items="${now['memberPersonalReports']}" varStatus="status"><c:if test="${now['serviceYear'] == memberPersonalReport.year}"><c:if test="${not empty monthInitialYear}">,</c:if><c:set var="monthInitialYear" value="true"/>'${memberPersonalReport.monthI18n}'</c:if></c:forEach>],
						data: [<c:forEach var="memberPersonalReport" items="${now['memberPersonalReports']}" varStatus="status"><c:if test="${now['serviceYear'] == memberPersonalReport.year}"><c:if test="${not empty hourMonthInitialYear}">,</c:if><c:set var="hourMonthInitialYear" value="true"/>${memberPersonalReport.totalMinutes/60}</c:if></c:forEach>],
						color: colors[0]
					}
				}, {
				 	y: ${now['finalYearPercent']},
					color: colors[1],
					drilldown: {
						name: 'year2',
						categories: [<c:forEach var="memberPersonalReport" items="${now['memberPersonalReports']}" varStatus="status"><c:if test="${(now['serviceYear']) + 1 == memberPersonalReport.year}"><c:if test="${not empty monthFinalYear}">,</c:if><c:set var="monthFinalYear" value="true"/>'${memberPersonalReport.monthI18n}'</c:if></c:forEach>],
						data: [<c:forEach var="memberPersonalReport" items="${now['memberPersonalReports']}" varStatus="status"><c:if test="${(now['serviceYear']) + 1  == memberPersonalReport.year}"><c:if test="${not empty hourMonthFinalYear}">,</c:if><c:set var="hourMonthFinalYear" value="true"/>${memberPersonalReport.totalMinutes/60}</c:if></c:forEach>],
						color: colors[1]
					}
				}];
		
		// Build the data arrays
		var browserData = [];
		var versionsData = [];
		for (var i = 0; i < data.length; i++) {
			// add browser data
			browserData.push({
				name: categories[i],
				y: data[i].y,
				color: data[i].color
			});
			// add version data
			for (var j = 0; j < data[i].drilldown.data.length; j++) {
				var brightness = 0.2 - (j / data[i].drilldown.data.length) / 5 ;
				versionsData.push({
					name: data[i].drilldown.categories[j],
					y: data[i].drilldown.data[j],
					color: Highcharts.Color(data[i].color).brighten(brightness).get()
				});
			}
		}
		
		// Create the chart
		dunutChart = new Highcharts.Chart({
			chart: {
				renderTo: 'personalReport-donut', 
				type: 'pie'
			},
			title: {
				text: 'Ano de Serviço de ${now['serviceYear']}'
			},
			yAxis: {
				title: {
					text: 'Percentual total de horas'
				}
			},
			plotOptions: {
				pie: {
					shadow: false
				}
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.point.name +'</b>: '+ Math.round(this.y) +' %';
				}
			},
			series: [{
				name: 'Years',
				data: browserData,
				size: '60%',
				dataLabels: {
					formatter: function() {
						return this.y > 5 ? this.point.name : null;
					},
					color: 'white',
					distance: -30
				}
			}, {
				name: 'Months',
				data: versionsData,
				innerSize: '60%',
				dataLabels: {
					formatter: function() {
						// display only if larger than 1
						return this.y > 1 ? '<b>'+ this.point.name +':</b> '+ Math.round(this.y) +'%'  : null;
					}
				}
			}]
		});
	});
</script>
<div id="personalReport-pie" style="width: 500px; height: 320px;"></div>
<div id="personalReport-donut" style="width: 500px; height: 320px;"></div>
</c:if>