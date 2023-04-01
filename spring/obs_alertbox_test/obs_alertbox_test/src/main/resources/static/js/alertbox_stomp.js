
var stompClient;

function openSocket(){
    var socket = new SockJS('/users')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/a', function(response) {
            console.log(response)
            console.log(JSON.parse(response.body));
        });
    })
}

function send(){
    console.log("sending")
    var text = document.getElementById("messageinput").value;
    stompClient.send("/app/user", {}, JSON.stringify({name : text}));
}

function closeSocket(){
    webSocket.close();
}

function writeResponse(text){
    messages.innerHTML += "<br/>" + text;
}
function setImage(path) {
    document.getElementById("img").src = "./images/" + path;
}
