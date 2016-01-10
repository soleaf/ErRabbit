<%--
  Created by IntelliJ IDEA.
  User: soleaf
  Date: 15. 8. 9.
  Time: 오후 5:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
    <link rel="stylesheet" href="/css/report.css" type="text/css"/>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script src="/js/report.js"></script>
    <script type="text/javascript">
        google.load("visualization", "1", {packages: ["corechart"]});
        google.setOnLoadCallback(drawChart);
        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['HOUR', 'FATAL', 'ERROR', 'WARN']
                <c:forEach items="${logLevelhourlySet}" var="item">
                , ['${item.key}h', ${item.value.level_FATAL}, ${item.value.level_ERROR}, ${item.value.level_WARN}]
                </c:forEach>
            ]);

            var options = {
                width: '100%',
                height: 200,
                chartArea: {width: '100%', height: 150},
                legend: {position: 'in'},
                titlePosition: 'in', axisTitlesPosition: 'in',
                hAxis: {min: 0, textStyle: {color: '#878787', fontSize: 10}},
                vAxis: {textPosition: 'in', min: 0, gridlines: {color: '#ffffff'}},
                curveType: 'function',
                colors: ['#ff5572', '#FF8166', '#ffbf5e']
            };

            var chart = new google.visualization.LineChart(document.getElementById('total_timeline_chart'));
            chart.draw(data, options);

            <c:forEach var="rabbit" items="${report.targets}" varStatus="step">
            var data_${step.index} = google.visualization.arrayToDataTable([
                ['HOUR', 'FATAL', 'ERROR', 'WARN']
                <c:forEach items="${rabbitLevelHourlySet.get(rabbit)}" var="item">
                , ['${item.key}h', ${item.value.level_FATAL}, ${item.value.level_ERROR}, ${item.value.level_WARN}]
                </c:forEach>
            ]);
            var chart_${step.index} = new google.visualization.LineChart(document.getElementById('${step.index}_timeline_chart'));
            chart_${step.index}.draw(data_${step.index}, options);
            </c:forEach>
        }
    </script>
</head>
<body class="report">
<jsp:include page="../common/nav.jsp">
    <jsp:param name="report" value="active"/>
</jsp:include>
<section class="page">
    <jsp:include page="side.jsp">
        <jsp:param name="list" value="active"/>
    </jsp:include>
    <div class="page-side-margin">
        <h2>${report.targetDateWithFormat}</h2>

        <div class="page-header">
            <h3 class="summary">Summary</h3>
        </div>
        <div class="chapter">
            <h4>Timeline</h4>

            <div id="total_timeline_chart" style="width: 100%; height: 200px;"></div>
            <h4>Log level rank</h4>
            <table class="table table-hover rank">
                <thead>
                <th class="level">level</th>
                <th class="rabbit">rabbit</th>
                <th class="className">class</th>
                <th class="count">count</th>
                </thead>
                <c:forEach var="rank" items="${report.logReport.get('graphic').ranks}">
                    <tr>
                        <td class="level ${rank.level}">${rank.level}</td>
                        <td class="name">${rank.rabbitIdSet}</td>
                        <td class="className"><a href="/log/list.err?id=${rank.rabbitIdSet}&y=${report.targetYear}&m=${report.targetMonth+1}&d=${report.targetDay}&filter=true&filter_class=${rank.className}&filter_level=${rank.level}"><span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span> ${rank.classNameOnly}</a>
                        </td>
                        <td class="count">${rank.count}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="page-header">
            <h3>Detail</h3>
        </div>
        <c:forEach var="rabbit" items="${report.targets}" varStatus="step">
            <div class="chapter">
                <h4>${rabbit} <small><a href="/log/list.err?id=${rabbit}&y=${report.targetYear}&m=${report.targetMonth+1}&d=${report.targetDay}"><span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span></a></small></h4>

                <div id="${step.index}_timeline_chart" style="width: 100%; height: 200px;" class="chart"></div>
                <table class="table table-hover rank">
                    <thead>
                    <th class="level">level</th>
                    <th class="rabbit">rabbit</th>
                    <th class="className">class</th>
                    <th class="count">count</th>
                    </thead>
                    <c:forEach var="rank" items="${report.logReport.get('graphic').ranks}">
                        <c:if test="${rank.rabbitIdSet == rabbit}">
                            <tr>
                                <td class="level ${rank.level}">${rank.level}</td>
                                <td class="name">${rank.rabbitIdSet}</td>
                                <td class="className"><a href="/log/list.err?id=${rank.rabbitIdSet}&y=${report.targetYear}&m=${report.targetMonth+1}&d=${report.targetDay}&filter=true&filter_class=${rank.className}&filter_level=${rank.level}"><span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span> ${rank.classNameOnly}</a>
                                <td class="count">${rank.count}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </div>
        </c:forEach>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>