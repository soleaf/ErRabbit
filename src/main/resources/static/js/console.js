/**
 * ErRabbit Web Console
 * soleaf, mintcode.org
 * https://github.com/soleaf/ErRabbit
 * console.js
 */

var stompClient = null;
var socket = null;
var retry = 0; // connect retry count

/**
 * Init
 */
$(document).ready(function () {

    disconnect();
    connect();
    initLogModalButton();
    logEvent();

    // Extend session long polling
    setInterval(
        function () {
            extendSession();
        }
        , 1000 * 60 * 10);
});

/***
 * Connection status UI
 * @param connected
 */
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

/**
 * Connect
 */
function connect() {
    socket = new SockJS('/console');
    stompClient = Stomp.over(socket);
    stompClient.heartbeat.outgoing = 20000; // client will send heartbeats every 20000ms
    stompClient.heartbeat.incoming = 0;     // client does not want to receive heartbeats
    stompClient.debug = function (str) {
    };
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/console', function (message) {
                appendReports(message.body);
            }
        );
    }, function () {
        setTimeout(function () {
            connect();
        }, 10000);
    });

    socket.onclose = function () {
        //todo : notify close as window
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

/**
 * Disconnect socket
 */
function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

/**
 * Append received log to UI
 * @param message
 */
function appendReports(message) {
    $("#waiting").fadeOut();
    // Remove Over line
    if ($("#log-list .log").length > 1000) {
        $("#log-list .log").last().remove();
    }
    $("#log-list").prepend(message);
    logEvent();
}

/**
 * Init log event
 */
function logEvent() {
    $("#log-list .log[data-e='true']").click(function () {
        // Report Detail Information Layer Toggle
        var row = $(this);
        $.get(row.data('poload'), function (d) {
            sessionExpireCheck(d);

            // init filter button
            $("#popover_log_btn_showeothers").hide();
            $("#popover_log_btn_hideothers").show();
            $("#popover_log_btn_text").show();
            $("#popover_log_btn_graph").hide();

            $("#popover_log_title").html(row.find(".time").text() + " " + row.find(".level").text());
            $("#popover_log_body").html(d);
            $("#popover_log").modal();

        });
    });

    // log list item's category event
    $("#log-list .log .categoryName").click(function () {
        location.href=$(this).attr("data-url");
    });

    // log list item's category event
    $("#log-list .log .rabbit_id").click(function () {
        location.href="/log/list.err?id="+$(this).text();
    });
}

function sessionExpireCheck(data) {
    if (data.indexOf("id=\"login_page\"") > -1) {
        alert("Session has expired");
        window.location.href = "/login.err";
    }
}

/**
 * Init log modal buttons
 */
function initLogModalButton() {
    $("#popover_log_btn_graph").click(function () {
        $("#popover_log_body .text").fadeOut(function () {
            $("#popover_log_body .graph").fadeIn();
        });
        $("#popover_log_btn_text").show();
        $(this).hide();
    });
    $("#popover_log_btn_text").click(function () {
        $("#popover_log_body .graph").fadeOut(function () {
            $("#popover_log_body .text").fadeIn();
        });
        $("#popover_log_btn_graph").show();
        $(this).hide();
    });
    $("#popover_log_btn_hideothers").click(function () {
        $("#popover_log_body .another-package-set").slideUp();
        ;
        $("#popover_log_btn_showeothers").show();
        $(this).hide();
    });
    $("#popover_log_btn_showeothers").click(function () {
        $("#popover_log_body .another-package-set").slideDown();
        $("#popover_log_btn_hideothers").show();
        $(this).hide();
    });
}

/**
 * Extend session log polling
 */
function extendSession() {
    $.ajax({
        type: "GET",
        url: "/console/session.err",
        success: function (data) {

        }
        , fail: function () {
            // todo show erormessage
        }
    });

}
