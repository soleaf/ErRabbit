<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
    <script src="/js/analysis.js"></script>
</head>
<body class="analysis">
<jsp:include page="../common/nav.jsp">
    <jsp:param name="anal" value="active"/>
</jsp:include>
<section class="page">
    <div class="page-side page-side-wide">
        <!-- option -->
        <form action="/anal/aggregation" method="post" id="frm_anal">
            <input type="hidden" name="rabbit" value=""/>
            <input type="hidden" name="groupBy" value="" id="groupBy"/>

            <div class="page-side-element-head"><span class="glyphicon glyphicon-filter" aria-hidden="true"></span>
                FILTER
            </div>
            <div class="page-side-element">
                <div class="form-group">
                    <label class="page-side-sub-element-head">Rabbit</label>
                    <div class="page-side-sub-element">
                        <div class="btn-group btn-block btn-sm no-padding">
                            <button type="button" class="btn btn-default dropdown-toggle btn-block btn-sm"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span id="frm_rabbit_button">ALL</span> <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" style="width:100%;" id="frm_rabbit_list">
                                <c:forEach var="row" items="${rabbitList}">
                                    <li data-value="${row.id}" data-label="${row.id}"><a>${row.id}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="page-side-sub-element-head">Level</label>

                    <div class="page-side-sub-element">
                        <div class="row no-margin">
                            <div class="col-md-5">
                                <label class="checkbox-inline">
                                    <input name="level_trace" type="checkbox" value="true"> trace
                                </label>
                            </div>
                            <div class="col-md-5">
                                <label class="checkbox-inline">
                                    <input name="level_debug" type="checkbox" value="true"> debug
                                </label>
                            </div>
                        </div>
                        <div class="row no-margin">
                            <div class="col-md-5">
                                <label class="checkbox-inline">
                                    <input name="level_info" type="checkbox" value="true"> info
                                </label>
                            </div>
                            <div class="col-md-5">
                                <label class="checkbox-inline">
                                    <input name="level_warn" type="checkbox" value="true"> warn
                                </label>
                            </div>
                        </div>
                        <div class="row no-margin">
                            <div class="col-md-5">
                                <label class="checkbox-inline">
                                    <input name="level_error" type="checkbox" value="true"> error
                                </label>
                            </div>
                            <div class="col-md-5">
                                <label class="checkbox-inline">
                                    <input name="level_fatal" type="checkbox" value="true"> fatal
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="page-side-sub-element-head">Date</label>

                    <div class="page-side-sub-element">
                        <div>
                            <div class="input-group input-sm no-padding">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"
                                                                      aria-hidden="true"></span></span>
                                <input name="date_begin" type="text" class="form-control input-sm"
                                       aria-describedby="inputGroupSuccess1Status" placeholder="2015-01-01">
                            </div>
                        </div>
                        <div style="text-align: center;color:#CCCCCC;">~</div>
                        <div>
                            <div class="input-group input-sm no-padding">
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-calendar"
                                        aria-hidden="true"></span></span>
                                <input name="date_end" type="text" class="form-control input-sm"
                                       aria-describedby="inputGroupSuccess1Status" placeholder="2015-01-01">
                            </div>
                        </div>
                    </div>
                </div>
                <%--<div class="form-group">--%>
                    <%--<label class="page-side-sub-element-head">Exception</label>--%>

                    <%--<div class="page-side-sub-element">--%>
                        <%--<input name="exception" type="checkbox" value="true"> Has ThrowableInfo--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>

            <div class="page-side-element-head page-side-element-head-border"><span class="glyphicon glyphicon-transfer"
                                                                                    aria-hidden="true"></span>
                GROUP BY
            </div>
            <div class="page-side-element">
                <label class="page-side-sub-element-head">Selected</label>

                <div class="page-side-sub-element">
                    <ul id="groupby_selected">

                    </ul>
                </div>
                <label class="page-side-sub-element-head">Available</label>

                <div class="page-side-sub-element">
                    <ul id="groupby_available">
                        <li><input type="checkbox" value="rabbitId" data-label="rabbit"> rabbit</li>
                        <li><input type="checkbox" value="loggingEventDateInt" data-label="date"> date</li>
                        <li><input type="checkbox" value="loggingEvent.level" data-label="level"> level</li>
                        <li><input type="checkbox" value="loggingEvent.categoryName" data-label="className"> className
                        </li>
                    </ul>
                </div>
            </div>
            <div class="page-side-element page-side-sub-element">
                <button id="run" type="button" class="btn btn-primary btn-block btn-sm">RUN
                </button>
            </div>
        </form>
        <div style="clear:both;"></div>
    </div>

    <div class="result" id="result">

    </div>

</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>