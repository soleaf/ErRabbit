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
<section class="page" id="login_page">
    <div class="jumbotron login-jumbotron">
        <div class="container login-banner">
            <h1>ErRabbit</h1>
            <p>Check unaware errors<br/>
            Visual tracking remote logging service using Log4j</p>
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
        <form action="<c:url value='/login_process' />" method='POST'>
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
        </form>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>