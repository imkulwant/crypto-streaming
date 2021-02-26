var ws;
var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect_old() {
    //connect to stomp where stomp endpoint is exposed
    var socket = new WebSocket("ws://localhost:4040/crypto-currencies");
    ws = Stomp.over(socket);

    //usernaeme and password
    ws.connect({}, function(frame) {

        ws.subscribe("/topic/reply", function(msg) {
            alert(msg);
            showGreeting(msg.body);
        });

        setConnected(true);
    });
}

function connect() {

    const url = 'ws://localhost:4040/crypto-currencies';

    const stompConfig = {

        connectHeaders: {},
        brokerURL: url,
        reconnectDelay: 50000,
        heartbeatIncoming: 5000,
        heartbeatOutgoing: 5000,
        onConnect(frame) {

            const subscription = stompClient.subscribe(

                "/topic/reply",
                function(msg) {
                    alert(msg);
                    showGreeting(msg.body);
                }, {}

            );

        },

        onDisconnect: (frame) => {
            console.log("Stomp Disconnect", frame);
        },
        onStompError: (frame) => {
            console.log("Stomp error", frame);
        },
        onWebSocketClose: (frame) => {
            console.log("Stomp WebSocket Closed", frame);
        },
        onWebSocketError: (frame) => {
            console.log("Stomp WebSocket Error", frame);
        },

    };

    stompClient = new StompJs.Client(stompConfig);
    stompClient.activate();

}

function disconnect() {
    if (ws != null) {
        ws.close();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    var data = JSON.stringify({
        'name': $("#name").val()
    })
    ws.send("/app/message", {}, data);
}

function showGreeting(message) {
    $("#greetings").append(" " + message + "");
}

$(function() {
    $("form").on('submit', function(e) {
        e.preventDefault();
    });
    $("#connect").click(function() {
        connect();
    });
    $("#disconnect").click(function() {
        disconnect();
    });
    $("#send").click(function() {
        sendName();
    });
});