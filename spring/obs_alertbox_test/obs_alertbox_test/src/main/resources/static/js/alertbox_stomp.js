
var stompClient;

var token = getTokenFromPathname()
//console.log(token)

function getTokenFromPathname() {
    split_path = window.location.pathname.split("/") // value : /alertbox/{token}
    console.log(split_path)
    if (split_path.length != 3)
        return null

    return split_path[2]
}

function openSocket(){
    if (token == null)  return

    var socket = new SockJS('/users')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/' + token, function(response) {
            donationInfo = JSON.parse(response.body)
            //console.log(response)
            //console.log(JSON.parse(response.body));
            //console.log(donationInfo)
            setImage(donationInfo.image_path)
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
    document.getElementById("img").src = path;
}
