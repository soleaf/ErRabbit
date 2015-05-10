<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <h1>ErRabbit</h1>
    <c:if test="${empty param.hide}">
    <nav>
        <ul>
            <li class="${param.rabbit}"><a href="/rabbit/list.err">Rabbits</a></li>
            <li class="${param.alarm}"><a href="/alarm/intro">Alarms</a></li>
        </ul>
    </nav>
    </c:if>
</header>