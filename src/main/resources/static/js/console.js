var stompClient = null;

$(document).ready(function(){
    disconnect();
    connect();
});

function setConnected(connected) {
    if (connected){
        $("#con_success").css("display", "inline-block");
        $("#con_fail").hide();
        $("#con_connecting").hide();
        $("#waiting").fadeIn();
    }
    else{
        $("#con_success").hide();
        $("#con_fail").css("display", "inline-block");
        $("#con_connecting").hide();
    }
}

function connect() {
    var socket = new SockJS('/console');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/console', function(message){
            showGreeting(message.body);
        }
        );
    }, function(){
        console.log('STOMP: ' + error);
        disconnect();
        setTimeout(function(){
            connect();
            console.log('STOMP: Reconecting in 10 seconds');
        }, 10000);
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showGreeting(message) {
    $("#waiting").fadeOut();
    $("#report-list").prepend(message);
}

