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
        <h3>${title}</h3>
        <p>EventMapping is defining event condition.<br/>and after creating can link actions</p>
        <div class="form-horizontal event-form">
            <form class="form" action="${action}">
                <c:if test="${not empty modifying}">
                    <c:set var="rabbitIdSet" value="${rabbit.id}"/>
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
                    <label class="control-label">EVENT CONDITION</label>
                    <div class="sub-form">
                        <label for="rabbitSetButton" class="control-label">Target Rabbits</label>
                        <button id="rabbitSetButton" type="button" class="btn btn-block btn-default" role="button" data-toggle="modal" data-target="#changeRabbitModal">Select</button>
                        <input name="rabbitSet" type="hidden" id="rabbitSet"/>
                    </div>
                    <div class="sub-form">
                        <label for="level" class="control-label">Threshold logging level</label>
                        <select class="form-control" id="level" name="level">
                            <option value="fatal">FATAL</option>
                            <option value="error">ERROR</option>
                            <option value="warn">WARN</option>
                            <option value="info">INFO</option>
                        </select>
                        <span class="help-block">If you select 'INFO', logging level with INFO, WARN, ERROR, FATAL will be accepted.</span>
                    </div>
                    <div class="sub-form">
                        <label for="class" class="control-label">Matching Class</label>
                        <input type="text" class="form-control" id="class" name="matchClass"
                               placeholder="org.mintcode.errabbit.Application" value="${matchClass}">
                        <span class="help-block">Full class path. If you blank this form, any class logs will be accepted</span>
                    </div>
                    <div class="sub-form">
                        <label for="message" class="control-label">Including Message</label>
                        <input type="text" class="form-control" id="message" name="message" value="${message}">
                        <span class="help-block">Check include this text with logging message. If you blank this form, any messages logs will be accepted</span>
                    </div>
                    <div class="sub-form">
                        <label>
                            <input type="checkbox" name="exception" value="true" ${exception}/>
                            Has exception only
                        </label>
                    </div>
                </div>

                <div class="main-form">
                    <label class="control-label">THRESHOLD</label>
                    <div class="sub-form">
                        <label for="thresholdCount" class="control-label">Threshold Count</label>
                        <input id="thresholdCount" type="number" class="form-control" name="thresholdCount" value="1" min="1">
                    </div>
                    <div class="sub-form">
                        <label for="period" class="control-label">Period (unit: seconds)</label>
                        <input id="period" type="number" class="form-control" name="period" value="1" min="0">
                    </div>
                </div>

                <div class="main-form">
                    <label class="control-label">OPTION</label>
                    <div class="sub-form">
                        <label for="sleep" class="control-label">Sleep after fire</label>
                        <input id=sleep" type="number" class="form-control" name="sleep" value="1" min="0">
                        <span class="help-block">ddd</span>
                    </div>

                    <div class="sub-form">
                        <label>
                            <input type="checkbox" name="active" value="true" ${active}/>
                            Active
                        </label>
                        <span class="help-block">Active or inactive,But this status is applied after run rebuild eventstream or restart ErRabbit</span>
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