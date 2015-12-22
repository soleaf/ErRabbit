<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="page_total" value="${reports.getTotalPages()}"/>
<input type="hidden" id="page_call" value="${param.page}"/>
<input type="hidden" id="logs_cnt" value="${reports.totalElements}"/>
<c:if test="${empty reports.content}">
    <div class="empty">EMPTY</div>
</c:if>
<c:if test="${not empty reports.content}">
    <c:forEach var="item" items="${reports.content}" varStatus="status">
        <li class="log" data-id="${item.id}" data-e="<c:if test="${not empty item.loggingEvent.throwableInfo}">true</c:if>"  data-poload="/log/popover_data?id=${item.id}">
            <span role=button class="time" data-toggle="popover" data-placement="right" data-trigger="hover" title="Server collecting time" data-content="${format.format(item.loggingEvent.timeStampDate)}">${format.format(item.collectedDate)}</span>
            <span class="level ${item.loggingEvent.level} <c:if test="${not empty item.loggingEvent.throwableInfo}"> has_exception</c:if>" data-toggle="popover" data-placement="right" data-trigger="hover" title="Apply filter" data-content="by loggingEvent level">${item.loggingEvent.level}</span>
            <div class="contgroup">
                <span class="categoryName" data-toggle="popover" data-placement="right" data-trigger="hover" title="Apply filter" data-content="by class name">${item.loggingEvent.categoryName}</span>
                <span class="message" <c:if test="${not empty item.loggingEvent.throwableInfo}">data-toggle="popover" data-placement="top" data-trigger="hover" title="Avaliable Visual exception view" data-content="Click to trace exception" </c:if>>${item.loggingEvent.renderedMessage}</span>
            </div>
        </li>
    </c:forEach>
</c:if>
