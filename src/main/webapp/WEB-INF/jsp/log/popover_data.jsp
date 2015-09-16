<%--
  Created by IntelliJ IDEA.
  User: soleaf
  Date: 15. 7. 18.
  Time: 오후 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty log.loggingEvent.throwableInfo}">
    <div class="log-detail-popup" data-id="${log.id}">
        <div class="message">
            ${log.loggingEvent.renderedMessage}
        </div>
        <div class="ThrowableInfo">
            <div class="graph">
                <div class="threadName">threadName: ${log.loggingEvent.threadName}</div>
                <ul>
                    <li>
                        <div class="exception"> <span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span> ${log.loggingEvent.throwableInfo.rep[0]}
                        </div>
                    </li>
                    <c:forEach var="stack" items="${graphs}" varStatus="status">
                        <li class="<c:if test="${not stack.isDefaultHidden()}">another-package-set</c:if>">
                            <div class='arrow <c:if test="${stack.isDefaultHidden()}">base-package-arrow</c:if>'>
                                <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span></div>
                            <div class='class <c:if test="${stack.isDefaultHidden()}">base-package</c:if>'>
                                <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
                                <div class="class_name_set">
                                    <span class="package_name"> ${stack.packageName}.</span><span class="class_name">${stack.className}</span>
                                </div>
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
                </ul>
            </div>
            <div class="text">
                <ul>
                    <c:forEach var="rep" items="${log.loggingEvent.throwableInfo.rep}" varStatus="status">
                        <li>${rep}</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</c:if>


