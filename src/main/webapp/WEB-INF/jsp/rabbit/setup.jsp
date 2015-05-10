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
        <jsp:param name="howto" value="active"/>
    </jsp:include>
    <div class="page-side-margin">

        <h3>How to make integerate with your Application</h3>
        Make your application connect to JMS

        <h4>1. Add Dependencies to maven <code>pom.xml</code> </h4>

        <label>pom.xml</label>
        <div class="well">
            &lt;dependency&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;org.apache.logging.log4j&lt;/groupId&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;log4j-core&lt;/artifactId&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;version&gt;2.1&lt;/version&gt;<br/>
            &lt;/dependency&gt;<br/>
            &lt;dependency&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;javax.jms&lt;/groupId&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;jms&lt;/artifactId&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;version&gt;1.1&lt;/version&gt;<br/>
            &lt;/dependency&gt;<br/>
            &lt;dependency&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;org.apache.activemq&lt;/groupId&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;activemq-core&lt;/artifactId&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;version&gt;5.7.0&lt;/version&gt;<br/>
            &lt;/dependency&gt;
        </div>

        <h4>2. Setup log4j2</h4>

        <p>(1) Declare 'JSM Appender' to <code>log4j2.xml</code> with your ActiveMQ <b>URL, userName, password</b> And Make sure '<b>queueBindingName</b>' be <i>'errabbit.report.<code>[RabbitID]</code>'</i>
        </p>

        <p>(2) And Add JMS Appender to Loggers.</p>

        <label>log4j2.xml</label>
        <div class="well">
            &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;Configuration status=&quot;info&quot; name=&quot;MyApp&quot; packages=&quot;org.mintcode.errabbit.example&quot;&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Appenders&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;JMS name=&quot;errabbit&quot; queueBindingName=&quot;errabbit.report.example&quot; factoryBindingName=&quot;ConnectionFactory&quot; providerURL=&quot;tcp://localhost:61616&quot; userName=&quot;sender&quot; password=&quot;senderpassword!&quot; /&gt;<br/>
    p;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/Appenders&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;Loggers&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Root level=&quot;error&quot;&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;AppenderRef ref=&quot;errabbit&quot;/&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/Root&gt;<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/Loggers&gt;<br/>
            &lt;/Configuration&gt;
        </div>

        <h4>3. Setup jndi.properties</h4>

        <p>(1) Make <code>java/main/resource/jndi.properties</code>, And Put it with above ActiveMQ Settings.</p>

        <label>jndi.properties</label>
        <div class="well">
        java.naming.factory.initial = org.apache.activemq.jndi.ActiveMQInitialContextFactory<br/>
        java.naming.provider.url = tcp://localhost:61616<br/>
        queue.errabbit.report.example=errabbit.report.example<br/>
        </div>

        <h4>4. Use In Application Code</h4>

        <p>ErRabbit is using log4j2 JMS Appender.<br/>
        And collect Exception Information. With error log.<br/>
        You cant log all (info, debug, trace .. etc). But, For your Application performance,
        Use only as exception error logging.</p>

        <p>(1) Get Log4j Logger</p>

        <div class="well">
        Logger logger = LogManager.getLogger(getClass());
        </div>

        <p>(2) Log error. With Exception. Just <code>`logger.error([message],e)`</code></p>

        <div class="well">
        try{<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;int a[] = new int[2];<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;System.out.println("Access element three :" + a[3]);<br/>
        }<br/>
        catch (Exception e){<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;logger.error(e.getMessage(),e);<br/>
        }
        </div>

        <h4>Example Project</h4>
        Check Out Example Project.
        <p>
            <a href="https://github.com/soleaf/ErRabbit-Example-log4j2" target="_blank">https://github.com/soleaf/ErRabbit-Example-log4j2</a>
        </p>

    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>