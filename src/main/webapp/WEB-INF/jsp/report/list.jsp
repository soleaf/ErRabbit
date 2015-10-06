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
    <script src="/js/report.js"></script>
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
        <div class="panel panel-default empty-alert" id="empty" >
            <div class="panel-heading">HELP</div>
            <div class="panel-body">
                <p>Click to setup report.</p>
                <p>Make daily report and check at every morning before work.</p>
            </div>
            <div class="panel-footer">
                <a href="/report/settings.err" class="btn btn-default btn-xs" role="butt on">SETUP REPORT</a>
            </div>
        </div>
        <ul id="report-list" class="report-list">

        </ul>
        <button id="report-feed" class="btn btn-default btn-xs" type="button" data-page="0">More Feed</button>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>