var stompClient;
var token = getTokenFromPathname()

var fadeIntervalID = 0;

window.onload = function(){
    openSocket()
}

function getTokenFromPathname() {
    split_path = window.location.pathname.split("/") // value : /alertbox/{token}
    console.log(split_path)
    if (split_path.length != 3)
        return null

    return split_path[2]
}

function openSocket(){
    if (token == null)  return

    var socket = new SockJS('/alertbox')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/donationinfo/' + token, function(response) {
            var div = document.getElementById("main_frame");
            div.style.opacity=0;

            donationInfo = JSON.parse(response.body)
            setImage(donationInfo.image_path)
            setMessage(donationInfo.donation_message)
            fadeIntervalID = setInterval(show,100);
            setTimeout(end,5000);
        });
    })
}

// client 에서 메시지 보낼 일이 있을지 모르겠지만, 혹시 몰라서 남겨둠.
function send(){
    var text = document.getElementById("messageinput").value;
    stompClient.send("/app/user", {}, JSON.stringify({name : text}));
}

function setImage(path) {
    document.getElementById("donationImage").src = path;
}
function setMessage(msg) {
    document.getElementById("donationMessage").innerText = msg;
}

function show(){
    var div = document.getElementById("main_frame");
    opacity = Number(window.getComputedStyle(div).getPropertyValue("opacity"));
    if(opacity<1){
        opacity = opacity+0.1;
        div.style.opacity=opacity;
    }
    else{
        clearInterval(fadeIntervalID);

    }
}

function hide(){
    var div = document.getElementById("main_frame");
    opacity = Number(window.getComputedStyle(div).getPropertyValue("opacity"));

    if(opacity>0){
        //Fade out 핵심 부분
        opacity = opacity-0.1;
        div.style.opacity=opacity;
    }
    else{
        clearInterval(fadeIntervalID);
    }
}
function end() {
    fadeIntervalID = setInterval(hide,100);
}
