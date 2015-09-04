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
    <jsp:param name="group" value="active"/>
  </jsp:include>

  <div class="page-side-margin">

    <c:if test="${not empty info}">
      <div class="alert alert-success" role="alert">${info}</div>
    </c:if>

    <c:if test="${not empty error}">
      <div class="alert alert-danger" role="alert">${error}</div>
    </c:if>

    <c:if test="${not empty list}">
      <ul class="list_group row">
        <c:forEach var="item" items="${list}" varStatus="status">
          <li class="col-md-4">
            ${item}
          </li>
        </c:forEach>
      </ul>
    </c:if>

  </div>
</section>

<!-- Form Modal -->
<div class="modal fade" id="formModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="">
            <input type="hidden" name="id" />
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">New Group</h4>
                </div>
                <div class="modal-body">
                    <label>name</label>
                    <input name="name" class="form-control" />
                </div>
                <div class="modal-footer">
                    <input type="hidden" name="id" id="clean_id" value=""/>
                    <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                    <button type="submit" class="btn btn-primary">OK</button>
                </div>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
</body>
</html>