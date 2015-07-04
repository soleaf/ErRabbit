var stompClient = null;
var socket = null;
var retry = 0;

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
        retry =0
    }
    else{
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
    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/console', function(message){
            showGreeting(message.body);
        }
        );
    }, function(){
        console.log('STOMP: Reconnecting in 10 seconds');
        setTimeout(function(){
            connect();
        }, 10000);

    });

    socket.onclose = function() {
        //todo : notify close as window
        console.log('STOMP: Reconnecting in 10 seconds');
        if (retry < 5){
            $("#con_connecting").css("display", "inline-block");
            $("#con_success").hide();
            $("#con_fail").hide();
            $("#con_connecting").hide();
            setTimeout(function(){
                connect();
                retry++;
            }, 10000);
        }
        else{
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

function showGreeting(message) {
    $("#waiting").fadeOut();
    $("#report-list").prepend(message);
}

