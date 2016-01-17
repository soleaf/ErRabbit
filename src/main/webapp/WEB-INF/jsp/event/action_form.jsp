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
        <h3>${title}</h3>
        <p>${desc}</p>
        <div class="form-horizontal event-form">
            <form class="form" action="${action}">
                <input type="hidden" name="className" value="${param.actionClassName}"/>
                <c:if test="${not empty modifying}">
                    <
                    c:set var="rabbitIdSet" value="${rabbit.id}"/>
                    <c:set var="basePackage" value="${rabbit.basePackage}"/>
                    <c:set var="groupId" value="${rabbit.group.id}"/>
                    <c:if test="${rabbit.collectionOnlyException}">
                        <c:set var="collectionOnlyException" value="checked"/>
                    </c:if>
                    <c:if test="${rabbit.hideOnConsole}">
                        <c:set var="hideOnConsole" value="checked"/>
                    </c:if>
                </c:if>
                <div class="main-form">
                    <label class="control-label">GENERAL</label>
                    <div class="sub-form">
                        <label for="name" class="control-label">Name</label>
                        <input type="text" class="form-control" id="name" name="name"
                                value="${name}">
                        <span class="help-block">Just name for identifying</span>
                    </div>
                </div>

                <div class="main-form">
                    <label class="control-label">OPTION</label>
                    <c:forEach var="item" items="${uiElements}">
                        <div class="sub-form">
                            <label class="control-label">${item.label}</label>
                            <input type="${item.valueType}" class="form-control" name="${item.name}"
                                   placeholder="${item.defaultValue}" value="">
                            <span class="help-block">${item.help}</span>
                        </div>
                    </c:forEach>
                </div>

                <c:if test="${not empty e}">
                    <div class="alert alert-warning alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <strong>ERROR</strong> ${e.message}
                    </div>
                </c:if>
                <div class="form_submit">
                    <button type="submit"s class="btn btn-primary">CONFIRM</button>
                    <button class="btn btn-default">CANCEL</button>
                </div>
            </form>
        </div>
    </div>
</section>
<!-- Rabbit Select Modal -->
<div class="modal fade" id="changeRabbitModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">CHANGE RABBIT</h4>
            </div>
            <div class="modal-body">
                <c:if test="${not empty groups}">
                    <c:forEach var="group" items="${groups}" varStatus="status">
                        <div class="rabbit-group">
                            <div class="rabbit-group-name">${group.name}</div>
                            <div class="row">
                                <c:forEach items="${group.rabbits}" var="rabbitItem">
                                    <label class="col-md-4 rabbit-item"><input name="rabbit_ids" type="checkbox" value="${rabbitItem.id}"/> ${rabbitItem.id}</label>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>