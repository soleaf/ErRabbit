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
            <ul class="mapping-list">
                <c:forEach items="${list}" var="item">
                    <li class="mapping-row">
                        <h4 data-toggle="tooltip" title="${status_title}"><c:choose>
                            <c:when test="${item.active}">
                                <span class="glyphicon glyphicon-ok-circle mapping_on" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="Active"></span>
                            </c:when>
                            <c:otherwise>
                                <span class="glyphicon glyphicon-remove-circle mapping_off" aria-hidden="true" data-toggle="tooltip" data-placement="right" title="Inactive"></span>
                            </c:otherwise>
                        </c:choose> ${item.name}</h4>
                        <div class="set-box">
                            <div class="set">
                                <div class="field-name">TARGET</div>
                                <div class="field-value">
                                    <c:forEach items="${item.condition.rabbitIdSet}" var="rabbit" varStatus="stat">
                                        <a href="/log/list.err?id=${rabbit}">${rabbit}</a><c:if test="${not stat.last}">,</c:if>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="set set-border">
                                <div class="field-name">CONDITION</div>
                                <div class="field-value">
                                    <div class="element row">
                                        <span class="element-name col-md-3">level:</span>
                                        <span class="element-value col-md-3" data-toggle="tooltip" title="Threshold level"><u>${item.condition.matchLevel}</u></span>
                                        <span class="element-name col-md-3">class:</span>
                                        <span class="element-value col-md-3" data-toggle="tooltip" title="Matching class">
                                            <c:choose><c:when test="${not empty item.condition.matchClass}"><u>specific</u></c:when><c:otherwise>any</c:otherwise></c:choose>
                                        </span>
                                    </div>
                                    <div class="element row">
                                        <span class="element-name col-md-3">message:</span>
                                        <span class="element-value col-md-3" data-toggle="tooltip" title="Matching message contents">
                                            <c:choose><c:when test="${not empty item.condition.includeMessage}"><u>specific</u></c:when><c:otherwise>any</c:otherwise></c:choose>
                                        </span>
                                        <span class="element-name col-md-3">exception:</span>
                                        <span class="element-value col-md-3" data-toggle="tooltip" title="Is only exception">
                                            <c:choose><c:when test="${item.condition.hasException}"><u>yes</u></c:when><c:otherwise>any</c:otherwise></c:choose>
                                        </span>
                                    </div>
                                    <div class="element row">
                                        <span class="element-name col-md-3">threshold:</span>
                                        <span class="element-value col-md-3" data-toggle="tooltip" title="Threshold count during period">${item.condition.thresholdCount}</span>
                                        <span class="element-name col-md-3">period:</span>
                                        <span class="element-value col-md-3" data-toggle="tooltip" title="Period">${item.condition.periodSec}s</span>
                                    </div>
                                    <div class="element row">
                                        <span class="element-name col-md-3">sleep:</span>
                                        <span class="element-value col-md-3" data-toggle="tooltip" title="Sleep sec after action">${item.condition.sleepSecAfterAction}s</span>
                                    </div>
                                </div>
                            </div>
                            <div class="set set-border">
                                <div class="field-name">ACTIONS <a href="action?id=${item.id}" data-toggle="tooltip" data-placement="top" title="Modify actions"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span></a></div>
                                <div class="field-value">
                                    <c:forEach items="${item.actions}" var="action" varStatus="stat">
                                        <a href="/event/action/modify.err?id=${action.id}" data-toggle="tooltip" data-placement="right" title="${action.typeName}">${action.name}</a><c:if test="${not stat.last}">,</c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <a class="set set-button" href="modify.err?id=${item.id}" data-toggle="tooltip" data-placement="auto" title="Modify event mapping">
                            <span class="glyphicon glyphicon-menu-right" aria-hidden="true">&nbsp;</span>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>