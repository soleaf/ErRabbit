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
        <jsp:param name="mapping" value="active"/>
    </jsp:include>
    <div class="page-side-margin">
        <div class="page-information">
            <h3>Event Mapping</h3>
            <p class="desc">
                Event Mapping is defining about event condition and link to actions.
                <br/>You can create event mapping and add action.
            </p>
            <div class="btn-group">
                <a type="button" role="button" class="btn btn-default btn-sm" href="add.err"><span
                        class="glyphicon glyphicon-plus"></span> Add Mapping
                </a>
            </div>
        </div>
        <c:if test="${not empty list}">
        <table class="table table-striped mapping-table">
            <thead>
                <th>Name</th>
                <th>Condition</th>
                <th>Actions</th>
                <th>Active</th>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="item">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.condition}</td>
                    <td>${item.actions}</td>
                    <td>${item.active}</td>
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