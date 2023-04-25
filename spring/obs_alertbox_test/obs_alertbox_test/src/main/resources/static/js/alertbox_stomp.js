var stompClient;
var token = getTokenFromPathname()

var fadeIntervalID = null;
var fadeoutTimeoutId = null;
var donationSoundAudio = document.getElementById("donation_audio");

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
            end()

            donationInfo = JSON.parse(response.body)
            setImage(donationInfo.image_path)
            setMessage(donationInfo.donation_message)
            playSound(donationInfo.donation_sound, donationInfo.tts_sound)
            fadeIn()
            fadeoutTimeoutId = setTimeout(fadeOut,5000);
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
        opacity = opacity-0.1;
        div.style.opacity=opacity;
    }
    else{
        clearInterval(fadeIntervalID);
    }
}

function clearFadeInterval() {
    if (fadeIntervalID != null) {
        clearInterval(fadeIntervalID);
        fadeIntervalID = null
    }
}

function clearFadeoutTimeout() {
    if (fadeoutTimeoutId != null) {
        clearTimeout(fadeoutTimeoutId)
        fadeoutTimeoutId = null
    }
}

function fadeIn() {
    clearFadeInterval()
    fadeIntervalID = setInterval(show,100);
}

function fadeOut() {
    clearFadeInterval()
    fadeIntervalID = setInterval(hide,100);
}

function end() {
    clearFadeInterval()
    clearFadeoutTimeout()
    var div = document.getElementById("main_frame");
    div.style.opacity=0;
}

function playSound(alarm_sound, tts_sound) {
    if (!donationSoundAudio.paused) {
        donationSoundAudio.pause()
    }
    donationSoundAudio.src = alarm_sound
    donationSoundAudio.removeEventListener('ended', arguments.callee);

    donationSoundAudio.addEventListener('ended', function() {
        donationSoundAudio.src = tts_sound
        donationSoundAudio.play();
        donationSoundAudio.removeEventListener('ended', arguments.callee);
    });

    donationSoundAudio.play()
    const playPromise = donationSoundAudio.play();
    if (playPromise !== undefined) {
        playPromise.then(() => {
            // 자동 재생이 시작되었을 때 실행할 코드
            donationSoundAudio.hidden = true;
        }).catch(error => {
            // 자동 재생이 방지되었을 때 실행할 코드
            donationSoundAudio.hidden = false;
        });
    }
}

