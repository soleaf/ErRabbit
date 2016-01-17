<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
    <link rel="stylesheet" href="/css/log.css" type="text/css"/>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script src="/js/log.js"></script>
</head>
<body class="log">
<jsp:include page="../common/nav.jsp">
    <jsp:param name="rabbit" value="active"/>
</jsp:include>
<div class="page-navi">
    <ul>
        <%--<li><span class="glyphicon glyphicon-th" aria-hidden="true"></span> <a href="/rabbit/list.err">ROOT</a></li>--%>
        <%--<li class="arrow"><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>&nbsp;</li>--%>
        <li class="page-navi-button page-navi-rabbit-selection" data-toggle="modal" data-target="#changeRabbitModal"><span class="glyphicon glyphicon-th"  aria-hidden="true"></span>&nbsp;${rabbit.id}</li>
        <li class="filter" data-toggle="modal" data-target="#filterModal" id="filter_button">
            <span class="glyphicon glyphicon-filter glyphicon" aria-hidden="true"></span>
            <span id="filter_button_text">FILTER</span>
        </li>
    </ul>
</div>
<section class="page page-navi-padding">
    <c:if test="${not empty rabbit}">
        <div class="page-side">
            <!-- Options and Calendar -->
            <div class="calendar">
                <c:choose>
                    <c:when test="${not empty yearList}">
                        <input type="hidden" name="cal_y" id="cal_y" value="${today_y}"/>
                        <input type="hidden" name="cal_m" id="cal_m" value="${today_m}"/>
                        <input type="hidden" name="cal_d" id="cal_d" value="${today_d}"/>

                        <div class="dropdown" id="dropdownMenu_year_dropdown">
                            <button class="btn btn-default dropdown-toggle btn-block" type="button"
                                    id="dropdownMenu_year" data-toggle="dropdown" aria-expanded="true">
                                <span class="value">${today_y}</span>
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu_year">
                                <c:forEach var="item" items="${yearList}" varStatus="status">
                                    <li role="presentation"><a role="menuitem" tabindex="-1" href="#"
                                                               data-value="${item}">${item}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="dropdown" id="dropdownMenu_month_dropdown">
                            <button class="btn btn-default dropdown-toggle btn-block" type="button"
                                    id="dropdownMenu_month" data-toggle="dropdown" aria-expanded="true">
                                <span class="value">${today_m}</span>
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu_month">
                                <c:forEach begin="1" end="12" varStatus="loop">
                                    <li role="presentation"><a role="menuitem" tabindex="-1" href="#"
                                                               data-value="${loop.index}">${loop.index}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="empty">EMPTY</div>
                    </c:otherwise>
                </c:choose>
                <div class="clear"></div>
                <c:if test="${not empty yearList}">
                    <ol id="log-calendar"></ol>
                </c:if>
            </div>
        </div>
        <div class="log-area" id="log-area" data-rabbitId="${rabbit.id}">

            <div class="log-timeLine" id="timeline">
                <!-- Log Time Line Area-->
                <div class="chart" id="chart"></div>
            </div>
            <div class="log-list-box">
                    <%--<div class="log-head" id="log-head"></div>--%>
                <ul class="log-list" id="log-list">
                    <!--  Log List Area -->
                </ul>
                <button id="log-feed" class="btn btn-default btn-xs" type="button" data-page="0">More Feed</button>
                <p id="page-status" class="page-status">page : <span id="status_page">0</span>/<span id="status_totalpage">0</span> (<span id="status_elements">0</span> logs)</p>
            </div>
        </div>
    </c:if>
</section>
<!-- Filter Modal -->
<div class="modal fade" id="filterModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Filter</h4>
            </div>
            <input type="hidden" id="filter_init" value="${param.filter}"/>

            <div class="modal-body">
                <div class="input-group">
                    <label class="control-label">LEVEL</label>
                    <select id="filter_level" name="level" class="form-control">
                        <option value="ALL" <c:if test="${param.filter_level == 'ALL'}">selected</c:if>>ALL</option>
                        <option value="TRACE" <c:if test="${param.filter_level == 'TRACE'}">selected</c:if>>TRACE</option>
                        <option value="DEBUG" <c:if test="${param.filter_level == 'DEBUG'}">selected</c:if>>DEBUG</option>
                        <option value="INFO" <c:if test="${param.filter_level == 'INFO'}">selected</c:if>>INFO</option>
                        <option value="WARN" <c:if test="${param.filter_level == 'WARN'}">selected</c:if>>WARN</option>
                        <option value="ERROR" <c:if test="${param.filter_level == 'ERROR'}">selected</c:if>>ERROR</option>
                        <option value="FATAL" <c:if test="${param.filter_level == 'FATAL'}">selected</c:if>>FATAL</option>
                    </select>
                </div>
                <hr/>
                <div class="input-group" style="width:100%">
                    <label class="control-label">CLASS NAME</label>
                    <input id="filter_class" name="class" type="text" class="form-control"
                           placeholder="org.mintcode.errabbit.Server" style="width:100%"
                            value="${param.filter_class}">
                </div>
            </div>
            <div class="modal-footer">
                <button id="filter_clear" type="button" class="btn btn-default">CLEAR</button>
                <button id="filter_apply" type="submit" class="btn btn-primary">APPLY</button>
            </div>
        </div>
    </div>
</div>

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
                                <a href="/log/list.err?id=${rabbitItem.id}"><div class="col-md-4 rabbit-item">${rabbitItem.id}</div></a>
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
<jsp:include page="popover_view.jsp"/>
</body>
</html>