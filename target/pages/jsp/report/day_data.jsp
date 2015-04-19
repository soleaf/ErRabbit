<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty dayCellList}">
    <div>
        <ul>
            <c:forEach var="item" items="${dayCellList}" varStatus="status">
                <li class="day" data-value="${item.dayOfMonth}">
                    ${item.dayOfMonth}
                    <c:if test="${not empty item.statistics}"><span class="badge">${item.statistics.calcTotal()}</span></c:if>
                </li>
            </c:forEach>
        </ul>
    </div>
</c:if>
