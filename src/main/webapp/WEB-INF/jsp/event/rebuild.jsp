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
        <jsp:param name="rebuild" value="active"/>
    </jsp:include>
    <div class="page-side-margin">
        <h3>Rebuild EventStream</h3>
        <p>After modifying mapping or action, You should rebuild eventstream or restart server to apply changes.<br/>Check as-is and current mappings and click rebuild button bellow.</p>

        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#current">CURRENT</button>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#tobe"> <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span> REBUILD</button>

        <!-- Modal -->
        <div class="modal fade" id="current" tabindex="-1" role="dialog" aria-labelledby="current">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Current</h4>
                    </div>
                    <div class="modal-body">
                        <ul class="checker-list">
                            <c:forEach items="${ec.eventStream.eventCheckers}" var="checker">
                                <c:if test="${checker.mapping.active}">
                                    <li>
                                        <b>${checker.mapping.name}</b>
                                        <div class="padding">
                                            condition<br/>
                                            <div class="padding">
                                                <div class="element">target
                                                    : ${checker.mapping.condition.rabbitIdSet}</div>
                                                <div class="element">matchLevel
                                                    : ${checker.mapping.condition.matchLevel}</div>
                                                <div class="element">matchClass
                                                    : ${checker.mapping.condition.matchClass}</div>
                                                <div class="element">includeMessage
                                                    : ${checker.mapping.condition.includeMessage}</div>
                                                <div class="element">hasException
                                                    : ${checker.mapping.condition.hasException}</div>
                                                <div class="element">thresholdCount
                                                    : ${checker.mapping.condition.thresholdCount}</div>
                                                <div class="element">periodSec
                                                    : ${checker.mapping.condition.periodSec}s
                                                </div>
                                                <div class="element">sleepSecAfterAction
                                                    : ${checker.mapping.condition.sleepSecAfterAction}s
                                                </div>
                                            </div>
                                        </div>
                                        <div class="padding">
                                            action<br/>
                                            <div class="padding">
                                                <c:forEach items="${checker.mapping.actions}" var="action"
                                                           varStatus="actionStat">
                                                    <div class="element">${action.name}</div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="tobe" tabindex="-1" role="dialog" aria-labelledby="tobe">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">REBUILD TO ...</h4>
                    </div>
                    <div class="modal-body">
                        <ul class="checker-list">
                            <c:forEach items="${es.eventCheckers}" var="checker">
                                <c:if test="${checker.mapping.active}">
                                    <li>
                                        <b>${checker.mapping.name}</b>
                                        <div class="padding">
                                            condition<br/>
                                            <div class="padding">
                                                <div class="element">target
                                                    : ${checker.mapping.condition.rabbitIdSet}</div>
                                                <div class="element">matchLevel
                                                    : ${checker.mapping.condition.matchLevel}</div>
                                                <div class="element">matchClass
                                                    : ${checker.mapping.condition.matchClass}</div>
                                                <div class="element">includeMessage
                                                    : ${checker.mapping.condition.includeMessage}</div>
                                                <div class="element">hasException
                                                    : ${checker.mapping.condition.hasException}</div>
                                                <div class="element">thresholdCount
                                                    : ${checker.mapping.condition.thresholdCount}</div>
                                                <div class="element">periodSec
                                                    : ${checker.mapping.condition.periodSec}s
                                                </div>
                                                <div class="element">sleepSecAfterAction
                                                    : ${checker.mapping.condition.sleepSecAfterAction}s
                                                </div>
                                            </div>
                                        </div>
                                        <div class="padding">
                                            action<br/>
                                            <div class="padding">
                                                <c:forEach items="${checker.mapping.actions}" var="action"
                                                           varStatus="actionStat">
                                                    <div class="element">${action.name}</div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                        <a href="rebuild_action.err" role="button" class="btn btn-primary">REBUILD</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>