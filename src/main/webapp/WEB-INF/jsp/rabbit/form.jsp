<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../common/header_include.jsp"/>
    <link rel="stylesheet" href="/css/rabbit.css" type="text/css"/>
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
        <p>Rabbit is a target application to track log.</p>
        <div class="form-horizontal rabbit-form">
            <form class="form" action="${action}">
                <c:if test="${not empty modifying}">
                    <c:set var="rabbitIdSet" value="${rabbit.id}"/>
                    <c:set var="basePackage" value="${rabbit.basePackage}"/>
                    <c:set var="loggerType" value="${rabbit.loggerType}"/>
                    <c:if test="${rabbit.collectionOnlyException}">
                        <c:set var="collectionOnlyException" value="checked"/>
                    </c:if>
                    <c:if test="${rabbit.hideOnConsole}">
                        <c:set var="hideOnConsole" value="checked"/>
                    </c:if>
                </c:if>
                <div class="main-form">
                    <label class="control-label">GENERAL</label>
                    <div class="sub-form">
                        <label for="id" class="control-label">ID</label>
                        <input type="text" class="form-control" id="id" name="id" placeholder="Rabbit ID" value="${rabbitIdSet}" ${idReadOnly}>
                        <span class="help-block">It's used to identify a rabbitIdSet on Log4j appender property.</span>
                    </div>
                    <div class="sub-form">
                        <label for="loggerType" class="control-label">LoggerType</label>
                        <select class="form-control" id="loggerType" name="loggerType">
                            <c:if test="${not empty loggerTypes}">
                                <c:forEach var="item" items="${loggerTypes}">
                                    <option value="${item.value}" <c:if test="${loggerTpe == item.value}">selected</c:if>>${item}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <span class="help-block">What is used to your application as logging component?</span>
                    </div>
                    <div class="sub-form">
                        <label for="basePackage" class="control-label">Base package</label>
                        <input type="text" class="form-control" id="basePackage" name="basePackage"
                               placeholder="org.mintcode.errabbit" value="${basePackage}">
                        <span class="help-block">It will be help you to recognize your package in throwable stack.</span>
                    </div>
                    <div class="sub-form">
                        <label for="group" class="control-label">Group</label>
                        <select class="form-control" id="group" name="group">
                            <option value="(none)">(none)</option>
                            <c:if test="${not empty groups}">
                                <c:forEach var="item" items="${groups}">
                                    <option value="${item.id}" <c:if test="${groupId == item.id}">selected</c:if>>${item.name}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <span class="help-block">Management group</span>
                    </div>
                </div>

                <div class="main-form">
                    <label class="control-label">OPTION</label>
                    <div class="sub-form">
                        <br/>
                        <label>
                            <input type="checkbox" name="onlyException" value="true" ${collectionOnlyException}/>
                            Collect only exception
                        </label>
                        <span class="help-block">Discard any other logs has no throwable exception</span>
                    </div>
                    <div class="sub-form">
                        <label>
                            <input type="checkbox" name="hideOnConsole" value="true" ${hideOnConsole}/>
                            Hide on console
                        </label>
                        <span class="help-block">Hide this rabbit's logs on console page</span>
                    </div>
                </div>

                <c:if test="${not empty param.error}">
                    <div class="alert alert-warning alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <strong>ERROR</strong> ${param.error}
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