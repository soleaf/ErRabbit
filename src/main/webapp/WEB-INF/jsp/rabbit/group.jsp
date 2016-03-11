<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
    <link rel="stylesheet" href="/css/rabbit.css" type="text/css"/>
    <script src="/js/group.js"></script>
</head>
<body class="rabbit rabbit-group">
<jsp:include page="../common/nav.jsp">
    <jsp:param name="rabbit" value="active"/>
</jsp:include>
<section class="page">

    <jsp:include page="side.jsp">
        <jsp:param name="group" value="active"/>
    </jsp:include>

    <div class="page-side-margin">
        <h3>GROUP</h3>

        <p>Managing groups</p>
        <c:if test="${not empty param.info}">
            <div class="alert alert-success" role="alert">${param.info}</div>
        </c:if>

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger" role="alert">${param.error}</div>
        </c:if>

        <p>
            <button id="new_group" type="button" role="button" class="btn btn-default btn-sm"><span
                    class="glyphicon glyphicon-plus"></span> NEW
            </button>
        </p>
        <table class="table table-striped group-table">
            <thead>
            <th>Name</th>
            <th width="170">Action</th>
            </thead>
            <tbody>
            <c:if test="${not empty list}">
                <c:forEach var="item" items="${list}" varStatus="status">
                    <tr>
                        <td>${item.name}<br/>(${item.id})</td>
                        <td>
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-default btn-xs" data_id="${item.id}"
                                        data_name="${item.name}" role="modify"><span
                                        class="glyphicon glyphicon-pencil"></span> MODIFY
                                </button>
                                <button type="button" class="btn btn-default btn-xs" data_id="${item.id}"
                                        data_name="${item.name}" role="delete"><span
                                        class="glyphicon glyphicon-remove"></span> DELETE
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
            <c:if test="${empty list}">
                <tbody>
                <tr>
                    <td colspan="2"><i>No gorup</i></td>
                </tr>
                </tbody>
            </c:if>
        </table>

    </div>
</section>

<!-- Form Modal -->
<div class="modal fade" id="formModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="">
            <input type="hidden" name="id"/>

            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">New Group</h4>
                </div>
                <div class="modal-body">
                    <label>name</label>
                    <input name="name" class="form-control"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                    <button type="submit" class="btn btn-primary">OK</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Delete Group</h4>
            </div>
            <div class="modal-body">
                Are you sure delete this group <b id="deleteModalName">group name</b>
            </div>
            <div class="modal-footer">
                <form action="/rabbit/group/delete.err">
                    <input type="hidden" name="id" value=""/>
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