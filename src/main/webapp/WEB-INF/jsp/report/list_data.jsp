<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="page_total" value="${list.getTotalPages()}"/>
<input type="hidden" id="page_call" value="${param.page}"/>
<c:if test="${empty list.content}">
    <div class="empty">EMPTY</div>
</c:if>
<c:if test="${not empty list.content}">
    <c:forEach var="item" items="${list.content}" varStatus="status">
        <li>
            <div class="panel panel-default <c:if test="${not item.read}">panel-primary</c:if>">
                <div class="panel-heading"><span class="glyphicon glyphicon-file" aria-hidden="true"></span> ${item.targetDateWithFormat}</div>
                <table class="table rank">
                    <thead>
                        <th class="level">level</th>
                        <th class="rabbit">rabbit</th>
                        <th class="className">class</th>
                        <th class="count">count</th>
                    </thead>
                    <c:forEach var="rank" items="${item.logReport.get('graphic').getRanksLimit(3)}">
                        <tr>
                            <td class="level ${rank.level}">${rank.level}</td>
                            <td class="name">${rank.rabbitId}</td>
                            <td class="className">${rank.classNameOnly}</td>
                            <td class="count">${rank.count}</td>
                        </tr>
                    </c:forEach>
                </table>
                <ul class="list-group">
                    <li class="list-group-item">
                        <b>TARGET</b><br/>
                        <c:forEach var="r" items="${item.targets}" varStatus="s"><c:if
                                test="${s.index > 0}">, </c:if>${r}</c:forEach>
                    </li>
                </ul>
                <div class="panel-body">
                    <div>
                        <a href="/report/detail?id=${item.id}" class="btn btn-primary btn-xs detail">READ MORE</a>
                    </div>
                </div>
                <div class="panel-footer">
                    <span class="date">Created at ${item.sendTime}</span>
                    <a class="delete" data-toggle="modal" data-target="#deleteModal" data-deleting-id="${item.id}" data-deleting-date="${item.targetDateWithFormat}">DELETE</a>
                </div>
            </div>
        </li>
    </c:forEach>
</c:if>
