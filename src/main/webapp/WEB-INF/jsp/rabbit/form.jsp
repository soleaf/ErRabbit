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
    <c:choose>
        <c:when test="${not empty modifying}">
            <c:set var="action" value="/rabbit/modify_action.err"/>
            <c:set var="title" value="Modify Rabbit"/>
            <c:set var="tab" value=""/>
            <c:set var="idReadOnly" value="readonly"/>
        </c:when>
        <c:otherwise>
            <c:set var="action" value="/rabbit/insert_action.err"/>
            <c:set var="title" value="Add New Rabbit"/>
            <c:set var="tab" value="active"/>
            <c:set var="idReadOnly" value=""/>
        </c:otherwise>
    </c:choose>
    <jsp:include page="side.jsp">
        <jsp:param name="add" value="${tab}"/>
    </jsp:include>
    <div class="page-side-margin">
        <h3>${title}</h3>
        <p>Rabbit is Target Application to track log.</p>

        <div class="form-horizontal rabbit-form">
            <form class="form" action="${action}">
                <c:if test="${not empty modifying}">
                    <c:set var="rabbitId" value="${rabbit.id}"/>
                    <c:set var="basePackage" value="${rabbit.basePackage}"/>
                    <c:if test="${rabbit.collectionOnlyException}">
                        <c:set var="collectionOnlyException" value="checked"/>
                    </c:if>
                </c:if>
                <div>
                    <label for="id" class="control-label">ID</label>
                    <input type="text" class="form-control" id="id" name="id" placeholder="Rabbit ID" value="${rabbitId}" ${idReadOnly}>
                    <span class="help-block">It's used to identify a rabbitId on Log4j appender property.</span>
                </div>
                <div>
                    <label for="basePackage" class="control-label">Base Package</label>
                    <input type="text" class="form-control" id="basePackage" name="basePackage"
                           placeholder="org.mintcode.errabbit" value="${basePackage}">
                    <span class="help-block">It will be help you to recognize your package in throwable stack.</span>
                </div>
                <div>
                    <label>
                        <input type="checkbox" name="onlyException" value="true" ${collectionOnlyException}/>
                        Collect only Exception
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
                    <button type="submit"s class="btn btn-primary">CONFIRM</button>
                    <button class="btn btn-default">CANCEL</button>
                </div>
            </form>
        </div>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>