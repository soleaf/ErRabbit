<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <c:if test="${empty param.hide}">
    <h1>ErRabbit</h1>
    <nav>
        <ul>
            <li class="${param.rabbit}"><a href="/rabbit/list.err">Rabbits</a></li>
            <li class="${param.alarm}"><a href="/alarm/intro">Alarms</a></li>
        </ul>
        <a class="logout" href="/logout">LOG OUT</a>
    </nav>
    </c:if>
</header>