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
                <a type="button" role="button" class="btn btn-default btn-sm" href="add.err">New Mapping
                </a>
            </div>
        </div>
        <div class="alertbox">
            <c:if test="${not empty param.info}">
                <div class="alert alert-success" role="alert">${param.info}</div>
            </c:if>

            <c:if test="${not empty param.error}">
                <div class="alert alert-danger" role="alert">${param.error}</div>
            </c:if>
        </div>
        <c:if test="${not empty list}">
        <table class="table mapping-table">
            <thead>
                <th width="2%" align="center">ON</th>
                <th width="15%">Name</th>
                <th width="38%">Targets</th>
                <th width="25%">Actions</th>
                <th width="20%">Edit</th>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="item">
                <tr>
                    <td align="center"><c:choose>
                        <c:when test="${item.active}">
                            <span class="glyphicon glyphicon-ok-circle mapping_on" aria-hidden="true"></span>
                        </c:when>
                        <c:otherwise>
                            <span class="glyphicon glyphicon-ban-circle mapping_off" aria-hidden="true"></span>
                        </c:otherwise>
                    </c:choose></td>
                    <td>${item.name}</td>
                    <td>
                        ${item.condition.rabbitIdSet}
                    </td>
                    <td>
                        <ul class="list-group">
                            <c:forEach items="${item.actions}" var="action">
                                <li class="list-group-item" data-toggle="tooltip" title="${action.typeName}"><span class="glyphicon glyphicon-send" aria-hidden="true"></span> ${action.name}</li>
                            </c:forEach>
                            <a href="action?id=${item.id}" class="list-group-item">ADD/DEL</a>
                        </ul>

                    </td>
                    <td>
                        <div class="btn-group" role="group">
                            <a href="modify?id=${item.id}" role="button" class="btn btn-default btn-sm">modify</a>
                            <a href="delete?id=${item.id}" role="button" class="btn btn-default btn-sm">delete</a>
                        </div>
                    </td>
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