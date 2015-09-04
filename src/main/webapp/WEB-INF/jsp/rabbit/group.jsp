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

<jsp:include page="../common/footer.jsp"/>
</body>
</html>