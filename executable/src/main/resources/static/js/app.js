var stompClient;

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

function connect() {

    const url = 'ws://localhost:4040/crypto-currencies';

    var data = $("#name").val();
    var destination = '/topic/reply/' + data;

    const stompConfig = {
        connectHeaders: {},
        brokerURL: url,
        reconnectDelay: 50000,
        heartbeatIncoming: 5000,
        heartbeatOutgoing: 5000,
        onConnect(frame) {
            const subscription = stompClient.subscribe(
                destination,
                function(msg) {
                    alert(msg);
                    showGreeting(msg.body);
                }, {});
        },

        onDisconnect: (frame) => {
            console.log("Stomp Disconnect: ", frame);
        },
        onStompError: (frame) => {
            console.log("Stomp Error: ", frame);
        },
        onWebSocketClose: (frame) => {
            console.log("Stomp WebSocket Closed: ", frame);
        },
        onWebSocketError: (frame) => {
            console.log("Stomp WebSocket Error: ", frame);
        },

    };

    stompClient = new StompJs.Client(stompConfig);
    stompClient.activate();
    setConnected(true);
}

function disconnect() {
    if (stompClient != null) {
        stompClient.deactivate();
    }
    setConnected(false);
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
});