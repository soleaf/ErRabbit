<%--
  Created by IntelliJ IDEA.
  User: soleaf
  Date: 15. 8. 9.
  Time: 오후 5:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
</head>
<body class="log">
<jsp:include page="../common/nav.jsp">
    <jsp:param name="log" value="active"/>
</jsp:include>
<section class="page">
    <jsp:include page="side.jsp">
        <jsp:param name="settings" value="active"/>
    </jsp:include>
    <div class="page-side-margin">
        <h3>Report Settings</h3>

        <div class="form-horizontal rabbit-form">
            <form class="form" action="${action}">
                <div>
                    <label for="day" class="control-label">TIME</label>

                    <div class="input-group">
                        <input type="text" class="form-control" id="inputGroupSuccess4"
                               aria-describedby="inputGroupSuccess4Status">
                        <span class="input-group-addon">: 00</span>
                    </div>
                    <span class="help-block">24 hour time</span>
                </div>
                <div>
                    <label for="basePackage" class="control-label">TARGETS</label>
                    <ul class="list-group rabbit-selection">
                        <c:if test="${not empty rabbits}">
                        <c:forEach items="${rabbits}" var="rabbit">
                        <li class="list-group-item">
                            <label class="checkbox">
                                <input type="checkbox" id="inlineCheckbox1" value="${rabbit.id}"> ${rabbit.id}
                            </label>
                        </li>
                        </c:forEach>
                        </c:if>
                </div>
                <div>
                    <label for="subscribers" class="control-label">SUBSCRIBERS</label>
                    <input type="text" class="form-control" id="subscribers">
                </div>
                <div>
                    <label for="acitve" class="control-label">ACIVE</label>
                    <div class="checkbox">
                        <label>
                            <input id="acitve"type="checkbox"> ACTIVE
                        </label>
                    </div>
                </div>
                <c:if test="${not empty e}">
                    <div class="alert alert-warning alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <strong>ERROR</strong> ${e.message}
                    </div>
                </c:if>
                <div class="form_submit">
                    <button type="submit" class="btn btn-primary">APPLY</button>
                    <button class="btn btn-default">RESET</button>
                </div>
            </form>
        </div>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>