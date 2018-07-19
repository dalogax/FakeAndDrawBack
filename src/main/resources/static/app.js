var stompClient = null;
var isMaster = false;

function restart(){
    isMaster=false;
    $("#createGame").prop("disabled", false);
    $("#deleteGame").prop("disabled", true);
    $("#joinGame").prop("disabled", false);
    $("#exitGame").prop("disabled", true);
    $("#joinForm").show();
    $("#roomCode").hide();
    $("#roomStatus").hide();
    $("#roomList").html("");
    $("#userName").value = "";
    $("#roomCodeText").html("");
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    restart();
    console.log("Disconnected");
}

function createGame(gameCode) {
    stompClient.send("/game/create");
}

function joinGame(gameCode) {
    stompClient.send("/game/join", {}, JSON.stringify({'roomCode': $("#gameCode").val(),'name': $("#userName").val()}));
}

function roomCreated(roomCode) {
    console.log('RoomCode Received='+roomCode);
    $("#roomCodeText").html(roomCode);
    $("#roomCode").show();
}

function userJoined(user) {
    console.log('UserJoined Received='+user);
    $("#roomList").append("<tr><td>" + user + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#createGame" ).click(function() { 
        $("#createGame").prop("disabled", true);
        $("#deleteGame").prop("disabled", false);
        $("#joinForm").hide();
        $("#roomStatus").show();
        isMaster = true;
        var socket = new SockJS('/fakeanddraw', [], { sessionId: 32 } );
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            var sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
            stompClient.subscribe('/user/' + sessionId + '/playerJoined', function (message) {
                console.log(message);
                userJoined(JSON.parse(message.body).userName);
            });
            stompClient.subscribe('/user/' + sessionId + '/roomCreated', function (message) {
                console.log(message);
                roomCreated(JSON.parse(message.body).roomCode);
            });
            createGame(sessionId); 
        });
    });
    $( "#deleteGame" ).click(function() { 
        disconnect(); 
    
    });
    $( "#joinGame" ).click(function() { 
        $("#joinGame").prop("disabled", true);
        $("#exitGame").prop("disabled", false);
        $("#createGame").prop("disabled", true);
        $("#joinForm").hide();
        var socket = new SockJS('/fakeanddraw', [], { sessionId: 67 } );
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            var sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
            stompClient.subscribe('/user/' + sessionId + '/matchStarted', function (greeting) {
                console.log(greeting);
                messageReceived(JSON.parse(greeting.body).content);
            });
            joinGame($("#gameCode").value); 
        }); 
    });
    $( "#exitGame" ).click(function() { 
        disconnect(); 
    });
});