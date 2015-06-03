<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
</head>
<body>
<jsp:include page="../common/nav.jsp">
    <jsp:param name="rabbit" value="active"/>
</jsp:include>
<section class="page">
    <jsp:include page="side.jsp">
        <jsp:param name="add" value="active"/>
    </jsp:include>
    <div class="page-side-margin">
        <h3>Add New Rabbit</h3>
        <p>Rabbit is Target Application to track log.</p>

        <div class="form-horizontal rabbit-form">
            <form class="form" action="/rabbit/insert_action.err">
                <div>
                    <label for="id" class="control-label">ID</label>
                    <input type="text" class="form-control" id="id" name="id" placeholder="Rabbit ID">
                    <span   class="help-block">It's used to identify a rabbitId on Log4j appender property.</span>
                </div>
                <div>
                    <label for="basePackage" class="control-label">Base Package</label>
                    <input type="text" class="form-control" id="basePackage" name="basePackage" placeholder="org.mintcode.errabbit">
                    <span class="help-block">It will be help you to recognize your package in throwable stack.</span>
                </div>
                <div>
                    <label>
                        <input type="checkbox" name="onlyException" value="true"/>
                        Collection only Exception
                    </label>
                    <span class="help-block">Discard any other logs has no Throwable exception</span>
                </div>
                <c:if test="${not empty e}">
                    <div class="alert alert-warning alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <strong>ERROR</strong> ${e.message}
                    </div>
                </c:if>
                <div class="form_submit">
                    <button type="submit" class="btn btn-primary">CONFIRM</button>
                    <button class="btn btn-default">CANCEL</button>
                </div>
            </form>
        </div>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>