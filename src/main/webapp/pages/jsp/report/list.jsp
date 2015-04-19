<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
    <script src="/pages/js/report.js"></script>
</head>
<body>
<jsp:include page="../common/nav.jsp">
    <jsp:param name="rabbit" value="active"/>
</jsp:include>
<div class="page-navi">
    <ul>
        <li><span class="glyphicon glyphicon-th" aria-hidden="true"></span> <a href="/rabbit/list.err">ROOT</a></li>
        <li class="arrow"><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>&nbsp;</li>
        <li>${rabbit.id}</li>
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
                            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu_year" data-toggle="dropdown" aria-expanded="true">
                                <span class="value">${today_y}</span>
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu_year">
                                <c:forEach var="item" items="${yearList}" varStatus="status">
                                    <li role="presentation"><a role="menuitem" tabindex="-1" href="#" data-value="${item}">${item}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="dropdown" id="dropdownMenu_month_dropdown">
                            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu_month" data-toggle="dropdown" aria-expanded="true">
                                <span class="value">${today_m}</span>
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu_month">
                                <c:forEach begin="1" end="12" varStatus="loop">
                                    <li role="presentation"><a role="menuitem" tabindex="-1" href="#" data-value="${loop.index}">${loop.index}</a></li>
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
                    <ol id="report-calendar"></ol>
                </c:if>
            </div>
        </div>
        <div class="report-area" id="report-area" data-rabbitId="${rabbit.id}">
            <div class="report-timeLine">
                <!-- Report Time Line Area-->
            </div>
            <div class="report-list-box">
                <ul class="report-list" id="report-list">
                <!--  Report List Area -->
                </ul>
                <button id="report-feed" class="btn btn-default btn-xs" type="button" data-page="0">More Feed</button>
            </div>
        </div>
    </c:if>

</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>