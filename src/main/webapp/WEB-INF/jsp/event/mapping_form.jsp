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
                <c:set value="SELECT" var="selectRabbitsLabel"/>
                <c:if test="${not empty rabbitIdSet}">
                    <c:set value="${rabbitIdSet}" var="selectRabbitsLabel"/>
                </c:if>
                <c:if test="${not empty mapping}">
                    <input type="hidden" name="id" value="${mapping.id}"/>
                </c:if>
                <div class="main-form">
                    <label class="control-label">GENERAL</label>
                    <div class="sub-form">
                        <label for="name" class="control-label">Name</label>
                        <input type="text" class="form-control" id="name" name="name"
                                value="${mapping.name}">
                        <span class="help-block">Just name for identifying</span>
                    </div>
                </div>

                <div class="main-form">
                    <label class="control-label">EVENT CONDITION</label>
                    <div class="sub-form">
                        <label for="rabbitSetButton" class="control-label">Target Rabbits</label>
                        <button id="rabbitSetButton" type="button" class="btn btn-block btn-default" role="button" data-toggle="modal" data-target="#changeRabbitModal">${selectRabbitsLabel}</button>
                        <input name="rabbitSet" type="hidden" id="rabbitSet" value="${rabbitIdSet}"/>
                    </div>
                    <div class="sub-form">
                        <label for="level" class="control-label">Threshold logging level</label>
                        <select class="form-control" id="level" name="level">
                            <option value="fatal" <c:if test="${mapping.condition.matchLevel=='fatal'}">selected</c:if>>FATAL</option>
                            <option value="error" <c:if test="${mapping.condition.matchLevel=='error'}">selected</c:if>>ERROR</option>
                            <option value="warn" <c:if test="${mapping.condition.matchLevel=='warn'}">selected</c:if>>WARN</option>
                            <option value="info" <c:if test="${mapping.condition.matchLevel=='info'}">selected</c:if>>INFO</option>
                        </select>
                        <span class="help-block">If you select 'INFO', logging level with INFO, WARN, ERROR, FATAL will be accepted.</span>
                    </div>
                    <div class="sub-form">
                        <label for="class" class="control-label">Matching Class</label>
                        <input type="text" class="form-control" id="class" name="matchClass"
                               placeholder="org.mintcode.errabbit.Application" value="${mapping.condition.matchClass}">
                        <span class="help-block">Full class path. If you blank this form, any class logs will be accepted</span>
                    </div>
                    <div class="sub-form">
                        <label for="message" class="control-label">Including Message</label>
                        <input type="text" class="form-control" id="message" name="message" value="${mapping.condition.includeMessage}">
                        <span class="help-block">Check include this text with logging message. If you blank this form, any messages logs will be accepted</span>
                    </div>
                    <div class="sub-form">
                        <label>
                            <input type="checkbox" name="exception" value="true" <c:if test="${mapping.condition.hasException}">checked</c:if>/>
                            Has exception only
                        </label>
                    </div>
                </div>

                <div class="main-form">
                    <label class="control-label">THRESHOLD</label>
                    <div class="sub-form">
                        <label for="thresholdCount" class="control-label">Threshold Count</label>
                        <input id="thresholdCount" type="number" class="form-control" name="thresholdCount"
                               value="<c:choose><c:when test="${not empty mapping.condition.thresholdCount}">${mapping.condition.thresholdCount}</c:when><c:otherwise>1</c:otherwise></c:choose>"
                               min="1" >
                    </div>
                    <div class="sub-form">
                        <label for="period" class="control-label">Period (unit: seconds)</label>
                        <input id="period" type="number" class="form-control" name="period"
                               value="<c:choose><c:when test="${not empty mapping.condition.periodSec}">${mapping.condition.periodSec}</c:when><c:otherwise>1</c:otherwise></c:choose>"
                               min="0">
                    </div>
                </div>

                <div class="main-form">
                    <label class="control-label">OPTION</label>
                    <div class="sub-form">
                        <label for="sleep" class="control-label">Sleep after fire</label>
                        <input id=sleep" type="number" class="form-control" name="sleep"
                               value="<c:choose><c:when test="${not empty mapping.condition.sleepSecAfterAction}">${mapping.condition.sleepSecAfterAction}</c:when><c:otherwise>1</c:otherwise></c:choose>"
                               min="0">
                        <span class="help-block">ddd</span>
                    </div>

                    <div class="sub-form">
                        <label>
                            <input type="checkbox" name="active" value="true" <c:if test="${mapping.active}">checked</c:if>/>
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
                    <button type="submit" class="btn btn-primary">CONFIRM</button>
                    <button class="btn btn-default">CANCEL</button>
                    <c:if test="${not empty mapping}">
                        <a href="#" role="button" class="btn btn-warning right" data-toggle="modal" data-target="#delete">DELETE</a>
                    </c:if>
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
<!-- Modal -->
<div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="delete">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Confirm</h4>
            </div>
            <div class="modal-body">
                Are you sure delete this event mapping?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                <a href="delete.err?id=${mapping.id}" role="button" class="btn btn-danger">DELETE</a>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>