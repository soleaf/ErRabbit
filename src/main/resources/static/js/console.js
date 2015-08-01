var stompClient = null;
var socket = null;
var retry = 0;

$(document).ready(function () {
    disconnect();
    connect();
    initReportModalButton();
    reportEvent();
    setInterval(
        function(){
            extendSession();
        }
        , 1000 * 60 * 10);
});

function setConnected(connected) {
    if (connected) {
        $("#con_success").css("display", "inline-block");
        $("#con_fail").hide();
        $("#con_connecting").hide();
        $("#waiting").fadeIn();
        retry = 0
    }
    else {
        $("#con_success").hide();
        $("#con_fail").css("display", "inline-block");
        $("#con_connecting").hide();
    }
}

function connect() {
    socket = new SockJS('/console');
    stompClient = Stomp.over(socket);
    stompClient.heartbeat.outgoing = 20000; // client will send heartbeats every 20000ms
    stompClient.heartbeat.incoming = 0;     // client does not want to receive heartbeats
    // from the server
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/console', function (message) {
                appendReports(message.body);
            }
        );
    }, function () {
        console.log('STOMP: Reconnecting in 10 seconds');
        setTimeout(function () {
            connect();
        }, 10000);

    });

    socket.onclose = function () {
        //todo : notify close as window
        console.log('STOMP: Reconnecting in 10 seconds');
        if (retry < 5) {
            $("#con_connecting").css("display", "inline-block");
            $("#con_success").hide();
            $("#con_fail").hide();
            $("#con_connecting").hide();
            setTimeout(function () {
                connect();
                retry++;
            }, 10000);
        }
        else {
            setConnected(false)
        }
    };
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function appendReports(message) {
    $("#waiting").fadeOut();
    $("#report-list").prepend(message);
    reportEvent();
}

function reportEvent(){
    $("#report-list .report").click(function () {

        // Report Detail Information Layer Toggle
        var row = $(this);
        $.get(row.data('poload'), function (d) {
            if ($($.parseHTML(d)).filter("#login_page").length > 0) {
                alert("Session is expired");
                window.location.href = "/login.err";
            }
            else{
                $("#popover_log_title").html(row.find(".time").text() + " " + row.find(".level").text());
                $("#popover_log_body").html(d);
                $("#popover_log").modal();
            }
        });
    });
}

function initReportModalButton(){
    $("#popover_log_btn_graph").click(function(){
        $("#popover_log_body .graph").show();
        $("#popover_log_body .text").hide();
    });
    $("#popover_log_btn_text").click(function(){
        $("#popover_log_body .graph").hide();
        $("#popover_log_body .text").show();
    });
}

function extendSession(){
    $.ajax({
        type : "GET",
        url : "/console/session.err",
        success: function (data) {
          console.log("extended session : ok");
        }
        ,fail: function(){
            console.log("extended session : fail");
        }
    });

}
