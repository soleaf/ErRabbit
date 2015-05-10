<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
</head>
<body>
<jsp:include page="../common/nav.jsp">
    <jsp:param name="alarm" value="active"/>
</jsp:include>
<section class="page">

    <div class="page-side-margin">
        <div class="page-header">
            <h1>Sorry Wait ...
                <small>Under the Construction</small>
            </h1>
        </div>
        <p>
            This Function is not implemented Now. If you check processing status, Visit GitHub.
        </p>
        <p><a class="btn btn-primary" role="button" href="https://github.com/soleaf/ErRabbit" target="_blank">GITHUB</a></p>
    </div>

</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>