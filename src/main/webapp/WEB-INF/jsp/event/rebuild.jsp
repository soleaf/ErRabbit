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
    <link rel="stylesheet" href="/css/event.css" type="text/css"/>
    <script src="/js/event.js"></script>
</head>
<body class="event">
<jsp:include page="../common/nav.jsp">
    <jsp:param name="event" value="active"/>
</jsp:include>
<section class="page">
    <jsp:include page="side.jsp">
        <jsp:param name="rebuild" value="active"/>
    </jsp:include>
    <div class="page-side-margin page-right-margin">
        <h3>Refresh EventStream</h3>
        <p>desc</p>

        <div class="panel panel-default">
            <div class="panel-heading">Running EventStream</div>
            <div class="panel-body">
                ${ec.eventStream.eventCheckers}
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">Rebuild to</div>
            <div class="panel-body">
                ${es.eventCheckers}
            </div>
        </div>
        <div class="form_submit">
            <form action="rebuild_action.err">
            <button type="submit" s class="btn btn-primary">CONFIRM</button>
            <button class="btn btn-default">CANCEL</button>
            </form>
        </div>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>