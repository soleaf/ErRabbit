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
    <script src="/js/event.mapping.action.js"></script>
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
        <h3>EventMapping Actions</h3>
        <p>name: ${mapping.name}</p>
        <p>id: ${mapping.id}</p>
        <div class="form-horizontal event-form">
            <div class="panel panel-primary">
                <div class="panel-heading">Linked actions</div>
                <ul id="linkedActions" class="list-group">
                    <c:if test="${not empty hasActions}">
                        <c:forEach items="${hasActions}" var="action">
                            <a class="list-group-item" role="button"
                               data-id="${action.id}">${action.name} <small>(${action.typeName})</small></a>
                        </c:forEach>
                    </c:if>
                </ul>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">Avaliable actions</div>
                <ul id="allActions" class="list-group">
                    <c:if test="${not empty allActions}">
                        <c:forEach items="${allActions}" var="action">
                            <a class="list-group-item" role="button"
                               data-id="${action.id}">${action.name} <small>(${action.typeName})</small></a>
                        </c:forEach>
                    </c:if>
                </ul>
            </div>
            <form id="submit" action="${action}">
                <input id="input-linkedActions" name="actions" type="hidden"/>
                <input name="id" type="hidden" value="${mapping.id}"/>
                <div class="form_submit">
                    <button type="submit" s class="btn btn-primary">CONFIRM</button>
                    <button class="btn btn-default">CANCEL</button>
                </div>
            </form>
        </div>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>