<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="page_total" value="${reports.getTotalPages()}"/>
<input type="hidden" id="page_call" value="${param.page}"/>
<c:if test="${empty reports.content}">
    <div class="empty">EMPTY</div>
</c:if>
<c:if test="${not empty reports.content}">
    <c:forEach var="item" items="${reports.content}" varStatus="status">
        <li class="report" data-id="${item.id}" data-poload="/log/popover_data?id=${item.id}">
            <span class="time">${format.format(item.loggingEvent.timeStampDate)}</span>
            <span class="level ${item.loggingEvent.level} <c:if test="${not empty item.loggingEvent.throwableInfo}"> has_exception</c:if>">${item.loggingEvent.level}</span>
            <div class="contgroup">
                <span class="categoryName">${item.loggingEvent.categoryName}</span>
                <span class="message">${item.loggingEvent.renderedMessage}</span>
            </div>
        </li>
    </c:forEach>
</c:if>
