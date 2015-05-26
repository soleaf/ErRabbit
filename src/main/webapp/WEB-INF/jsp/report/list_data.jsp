<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: soleaf
  Date: 2/21/15
  Time: 4:27 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="page_total" value="${reports.getTotalPages()}"/>
<input type="hidden" id="page_call" value="${param.page}"/>
<c:if test="${empty reports.content}">
    <div class="empty">EMPTY</div>
</c:if>
<c:if test="${not empty reports.content}">
    <c:forEach var="item" items="${reports.content}" varStatus="status">
        <li class="report" data-id="${item.id}">
            <span class="time">${format.format(item.loggingEvent.timeStampDate)}</span>
            <span class="level ${item.loggingEvent.level}">${item.loggingEvent.level}</span>
            <span class="categoryName">${item.loggingEvent.categoryName} ${item.loggingEvent.renderedMessage}</span>
        </li>

        <c:if test="${item.loggingEvent.throwableInfo}">
        <div class="report-detail-popup" data-id="${item.id}">
            <div class="ThrowableInfo">
                <ul class="report-tab">
                    <li class="active" data-tab="graph"><span class="glyphicon glyphicon-signal" aria-hidden="true"></span> Graph</li>
                    <li data-tab="text"><span class="glyphicon glyphicon-console" aria-hidden="true"></span> Text</li>
                </ul>
                <div class="close"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></div>
                <div class="graph">
                    <div class="threadName label">threadName: ${item.loggingEvent.threadName}</div>
                    <ul>
                        <li>
                            <div class="exception">
                                <span class="glyphicon glyphicon-warning-sign"
                                      aria-hidden="true"></span> ${item.loggingEvent.throwableInfo.rep[0]}
                            </div>
                        </li>
                        <c:forEach var="stack" items="${graphs.get(item)}" varStatus="status">
                            <li>
                                <div class='arrow <c:if test="${stack.isDefaultHidden()}">base-package-arrow</c:if>'>
                                    <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span></div>
                                <div class='class <c:if test="${stack.isDefaultHidden()}">base-package</c:if>'>
                                    <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
                                    <span> ${stack.className}</span>
                                    <c:forEach var="element" items="${stack.stackTraceElements}" varStatus="status">
                                        <ul>
                                            <li class="method">
                                                <span class="methodName">${element.methodName}</span>
                                                <span class="lineNumber">line.${element.lineNumber}</span>
                                            </li>
                                        </ul>
                                    </c:forEach>
                                </div>
                                <span class="fileName <c:if test="${stack.isDefaultHidden()}">base-package-fileName</c:if>">${stack.fileName}</span>
                            </li>
                        </c:forEach>
                        <button type="button" class="btn btn-default btn-xs base-package-toggle" data-id="${item.id}"
                                data-toggle="0">
                            TOGGLE HIDDEN TRACES
                        </button>
                    </ul>
                </div>
                <div class="text">
                    <ul>
                        <c:forEach var="rep" items="${item.loggingEvent.throwableInfo.rep}" varStatus="status">
                            <li>${rep}</li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        </c:if>
    </c:forEach>
</c:if>
