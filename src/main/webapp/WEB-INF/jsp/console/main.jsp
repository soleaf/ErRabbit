<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
    <script src="/js/sockjs-0.3.4.js"></script>
    <script src="/js/stomp.js"></script>
    <script src="/js/console.js"></script>
</head>
<body class="console">
<jsp:include page="../common/nav.jsp">
    <jsp:param name="console" value="active"/>
</jsp:include>
<%--<div class="page-navi">--%>
    <%--<ul>--%>
        <%--<li><span class="glyphicon glyphicon-globe" aria-hidden="true"></span> STATUS </li>--%>
        <%--<li id="con_connecting"><span class="label label-warning">CONNECTING</span></li>--%>
        <%--<li id="con_success"><span class="label label-success">CONNECTED</span></li>--%>
        <%--<li id="con_fail"><span class="label label-danger">DISCONNECTED</span></li>--%>
    <%--</ul>--%>
<%--</div>--%>
<section class="page">
    <div class="report-area report-area_nosidebar" id="report-area">

        <div class="report-timeLine">
            <!-- Report Time Line Area-->
        </div>
        <div class="report-list-box">
            <div id="waiting">
                <div class="panel panel-default">
                    <div class="panel-heading"><span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span> Waiting for new log</div>
                    <div class="panel-body">
                        <p>WebSocket connection is succeed. Log will be appear as arriving. Do not refresh this page.</p>
                    </div>
                </div>
            </div>
            <div style="clear:both;"></div>
            <ul class="report-list" id="report-list">
                <!--  Report List Area -->
                <c:if test="${not empty preload}">
                    <c:forEach items="${preload.content}" var="row">
                        ${row.toHTML(true)}
                    </c:forEach>
                </c:if>
            </ul>
        </div>
        <div id="retry">
            <div class="panel panel-warning">
                <div class="panel-heading">WebSocket connection failed</div>
                <div class="panel-body">
                    <p>Please retry with refresh this page</p>
                </div>
                <div class="panel-footer">
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../log/popover_view.jsp"/>
</body>
</html>