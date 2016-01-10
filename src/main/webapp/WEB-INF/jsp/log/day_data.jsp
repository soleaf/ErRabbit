<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty dayCellList}">
    <div>
        <ul>
            <c:forEach var="item" items="${dayCellList}" varStatus="status">
                <li id="day-${item.dayOfMonth}" class="day <c:if test='${selectedDay == item.dayOfMonth}'>active</c:if>" data-value="${item.dayOfMonth}" data-count="${item.statistics.calcTotal()}">
                    ${item.dayOfMonth}
                    <c:if test="${not empty item.statistics}"><span class="badge">${item.statistics.calcTotal()}</span></c:if>
                </li>
            </c:forEach>
        </ul>
    </div>
</c:if>
