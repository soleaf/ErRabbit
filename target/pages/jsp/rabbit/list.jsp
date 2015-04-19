<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<section class="page">
    <jsp:include page="side.jsp">
        <jsp:param name="list" value="active"/>
    </jsp:include>

    <div class="page-side-margin">
        <c:if test="${not empty list}">
            <ul class="list_rabbit row">
                <c:forEach var="item" items="${list}" varStatus="status">
                    <a href="/report/list.err?id=${item.id}">
                        <li class="col-md-4">
                            <div class="icon">
                                ${fn:substring(item.id, 0, 1)}
                            </div>
                            <span class="name">${item.id}</span>
                            <c:if test="${not empty lastStatics.get(item)}">
                                <span>Recent ${lastStatics.get(item).year}. ${lastStatics.get(item).month}. ${lastStatics.get(item).day} <span class="badge">${lastStatics.get(item).calcTotal()}</span></span>
                            </c:if>
                        </li>
                    </a>
                </c:forEach>
            </ul>
        </c:if>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>