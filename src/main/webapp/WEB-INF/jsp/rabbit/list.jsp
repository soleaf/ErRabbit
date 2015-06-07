<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
    <script src="/js/rabbit.js"></script>
</head>
<body class="rabbit">
<jsp:include page="../common/nav.jsp">
    <jsp:param name="rabbit" value="active"/>
</jsp:include>
<section class="page">
    <jsp:include page="side.jsp">
        <jsp:param name="list" value="active"/>
    </jsp:include>

    <div class="page-side-margin">

        <c:if test="${not empty info}">
            <div class="alert alert-success" role="alert">${info}</div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">${error}</div>
        </c:if>

        <c:if test="${not empty list}">
            <ul class="list_rabbit row">
                <c:forEach var="item" items="${list}" varStatus="status">
                        <li class="col-md-4">
                            <div class="icon">
                                ${fn:toUpperCase(fn:substring(item.id, 0, 1))}
                            </div>
                            <div class="conts title">
                                <h4 class="name">${item.id}</h4>
                            </div>
                            <div class="conts recent">
                                <c:choose>
                                    <c:when test="${not empty lastStatics.get(item)}">
                                        <span>Recent ${lastStatics.get(item).year}. ${lastStatics.get(item).month}. ${lastStatics.get(item).day}</span>
                                        <span>(${lastStatics.get(item).calcTotal()})</span>
                                        <c:if test="${!item.read}">
                                            <span class="new">UNREAD</span>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <span>No recent logs</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="conts action">
                                <a role="button" href="/log/list.err?id=${item.id}" ><span class="glyphicon glyphicon-inbox" aria-hidden="true"></span> LOGS</a>
                            </div>
                            <div class="conts action">
                                <a role="button" href="/rabbit/modify.err?id=${item.id}" ><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> SETTINGS</a>
                            </div>
                            <div class="conts action">
                                <a role="button" data-id="${item.id}" data-toggle="modal" data-target="#deleteModal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> REMOVE</a>
                            </div>
                        </li>
                </c:forEach>
            </ul>
        </c:if>

        <div class="how_to_integration_banner">
            <p>How to integrate with your Application</p>
            <a href="https://github.com/soleaf/ErRabbit#make-your-application-connect-jms" role="button" class="btn btn-primary" target="_blank">SEE HOW TO</a>
        </div>
    </div>
</section>

<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Delete Modal</h4>
            </div>
            <div class="modal-body">
                Are you sure delete it And related all reports?
                <div class="well well-sm"></div>
            </div>
            <div class="modal-footer">
                <form action="/rabbit/delete.err">
                    <input type="hidden" name="id" id="deleting_id" value=""/>
                    <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                    <button type="submit" class="btn btn-danger">DELETE</button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
</body>
</html>