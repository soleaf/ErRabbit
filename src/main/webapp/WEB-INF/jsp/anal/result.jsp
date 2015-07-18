<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${not empty req and empty result.result and empty e}">
    <!-- Not result -->
    <div class="panel panel-warning alert">
        <div class="panel-heading">
            <h3 class="panel-title">Ops have no result :d</h3>
        </div>
        <div class="panel-body">
            Have no result
        </div>
    </div>
</c:if>

<c:if test="${not empty req and not empty e}">
    <!-- Error -->
    <div class="panel panel-danger alert">
        <div class="panel-heading">
            <h3 class="panel-title">Error!</h3>
        </div>
        <div class="panel-body">
                ${e}
        </div>
    </div>
</c:if>

<c:if test="${not empty result.result}">
    <!-- result -->
    <h4>Result</h4>
    <c:set var="groups" value="${req.columns}"/>
    <table class="table table-bordered">
        <thead>
        <c:forEach var="group" items="${groups}">
            <th>${group}</th>
        </c:forEach>
        <th>count</th>
        </thead>
        <c:forEach var="row" items="${result.result}">
            <tr>
                <c:choose>
                    <c:when test="${fn:length(groups) == 1}">
                        <td>${row.get("_id")}</td>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="group" items="${groups}">
                            <td>${row.get(group)}</td>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <td>${row.get('count')}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
