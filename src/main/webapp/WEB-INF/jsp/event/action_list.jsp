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
        <jsp:param name="action" value="active"/>
    </jsp:include>
    <div class="page-side-margin">
        <div class="page-information">
            <h3>Event Action</h3>
            <p class="desc">
                You can make event actions to link to event mapping.
            </p>
            <div class="btn-group">
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        New Action <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="add.err?actionClassName=org.mintcode.errabbit.core.eventstream.event.action.SlackNotificationEventAction">Send Slack</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <c:if test="${not empty list}">
        <table class="table table-striped action-table">
            <thead>
                <th>Name</th>
                <th>Type</th>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="item">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.typeName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:if>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>