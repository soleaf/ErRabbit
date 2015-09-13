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
</head>
<body class="report">
<jsp:include page="../common/nav.jsp">
    <jsp:param name="report" value="active"/>
</jsp:include>
<section class="page">
    <jsp:include page="side.jsp">
        <jsp:param name="settings" value="active"/>
    </jsp:include>
    <div class="page-side-margin">
        <h3>Report Settings</h3>
        <div class="form-horizontal rabbit-form">
            <form class="form" method="POST" action="settings_action.err">
                <div class="main-form time">
                    <label for="timeHour" class="control-label">TIME</label>
                    <div class="input-group">
                        <input name="timeHour" type="text" class="form-control" id="timeHour"
                               aria-describedby="inputGroupSuccess4Status" value="${description.time.hour}">
                        <span class="input-group-addon">: 00</span>
                    </div>
                    <span class="help-block">24 hour time</span>
                </div>
                <div class="main-form rabbit-selection">
                    <label class="control-label">TARGETS</label>
                    <ul class="list-group">
                        <c:if test="${not empty rabbits}">
                        <c:forEach items="${rabbits}" var="rabbit">
                        <li class="list-group-item">
                            <label class="checkbox">
                                <input name="targets" type="checkbox" value="${rabbit.id}" <c:if test="${fn:contains(description.targets, rabbit.id)}">checked</c:if>> ${rabbit.id}
                            </label>
                        </li>
                        </c:forEach>
                        </c:if>
                    </ul>
                </div>
                <%--<div class="main-form">--%>
                    <%--<label for="subscribers" class="control-label">SUBSCRIBERS</label>--%>
                    <%--<input name="subscribers" type="text" class="form-control" id="subscribers" val="${description.subscribers}">--%>
                    <%--<span class="help-block">with comma</span>--%>
                <%--</div>--%>
                <%--<div class="main-form mail">--%>
                    <%--<label for="subscribers" class="control-label">MAIL SMTP SETTING</label>--%>
                    <%--<ul class="sub-form">--%>
                        <%--<li class="sub-form-item">--%>
                            <%--<label for="subscribers" class="control-label">HOST</label>--%>
                            <%--<input name="smtpHost" type="text" class="form-control">--%>
                        <%--</li>--%>
                        <%--<li class="sub-form-item">--%>
                            <%--<label class="control-label">ID</label>--%>
                            <%--<input name="stmpId" ype="text" class="form-control">--%>
                        <%--</li>--%>
                        <%--<li class="sub-form-item">--%>
                            <%--<label class="control-label">PASSWORD</label>--%>
                            <%--<input name="smtpPassword"  type="text" class="form-control">--%>
                        <%--</li>--%>
                        <%--<li class="sub-form-item">--%>
                            <%--<label class="control-label">Port for TLS/STARTTLS</label>--%>
                            <%--<input name="smtpTLSPort" type="text" class="form-control">--%>
                        <%--</li>--%>
                        <%--<li class="sub-form-item">--%>
                            <%--<label class="control-label">Port for SSL</label>--%>
                            <%--<input type="text" class="form-control">--%>
                        <%--</li>--%>
                        <%--<li class="sub-form-item">--%>
                            <%--<div class="checkbox">--%>
                                <%--<label>--%>
                                    <%--<input name="smtpUserAuth" type="checkbox"> Use Authentication--%>
                                <%--</label>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
                <div class="main-form">
                    <label for="acitve" class="control-label">ACIVE</label>
                    <div class="checkbox">
                        <label>
                            <input name="active" id="acitve" type="checkbox" <c:if test="${description.active}">checked</c:if>> ACTIVE
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