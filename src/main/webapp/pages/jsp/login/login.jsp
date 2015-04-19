<%--
  Created by IntelliJ IDEA.
  User: soleaf
  Date: 2015. 4. 13.
  Time: 오후 5:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
</head>
<body>
<jsp:include page="../common/nav.jsp">
    <jsp:param name="hide" value="1"/>
</jsp:include>
<section class="page">
    <div class="jumbotron">
        <div class="container">
            <h1>Check unaware errors</h1>
            <p>Visual Tracking Remote server log4j error logs</p>
            <p><a class="btn btn-default btn-lg" href="https://github.com/soleaf/ErRabbit" target="_blank" role="button">Learn more</a></p>
        </div>
    </div>
    <div class="login">
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">${error}</div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="alert alert-success" role="alert">${msg}</div>
        </c:if>
        <form action="<c:url value='/login_process.err' />" method='POST'>
            <div class="form-group">
                <label for="id">ID</label>
                <input name="username" type="text" class="form-control" id="id" placeholder="Enter id">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input name="password" type="password" class="form-control" id="password" placeholder="Password">
            </div>
            <%--<div class="checkbox">--%>
                <%--<label>--%>
                    <%--<input type="checkbox"> Remember Me!--%>
                <%--</label>--%>
            <%--</div>--%>
            <button type="submit" class="btn btn-default btn-block">SIGN IN</button>
            <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
        </form>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>