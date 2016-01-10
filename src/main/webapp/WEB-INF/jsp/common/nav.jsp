<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <c:if test="${empty param.hide}">
    <a href="/rabbit/list.err"><h1>ErRabbit</h1></a>
    <nav>
        <ul>
            <li class="${param.rabbit}"><a href="/rabbit/list.err">Rabbits</a></li>
            <li class="${param.event}"><a href="/event/mapping/list.err">EventStream</a></li>
            <li class="${param.console}"><a href="/console/main.err">Console</a></li>
            <li class="${param.anal}"><a href="/anal/main.err">Analysis</a></li>
            <li class="${param.report}"><a href="/report/list.err">Reports</a></li>
        </ul>
        <a class="logout" href="/logout">SingnOut</a>
    </nav>
    </c:if>
</header>