package com.example.obs_alertbox_test.controller;

import com.example.obs_alertbox_test.handler.AlertboxWebSocketHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

@Log4j2
@Controller
public class AlertboxWebsocketController {
    @GetMapping("/")
    public String index() {
        return "test";
    }

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/user")
    public void showUsers(String msg) {
        log.info("msg : " + msg);
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("name", "testuser");
        simpMessagingTemplate.convertAndSend("/topic/a", payload);

    }

    @GetMapping("/alertbox/{token}")
    public String webSocket(@PathVariable String token) {
        //TODO check token
        return "webSocket";
    }

    public WebSocketSession getSession(String name) {
        HashMap<String, WebSocketSession> hashMap = AlertboxWebSocketHandler.sessionMap;
        if(!hashMap.containsKey(name))  return null;
        WebSocketSession session = hashMap.get(name);
        if (!session.isOpen()) {
            hashMap.remove(name);
            return null;
        }

        return session;
    }

    @GetMapping("/imgControl/{name}/{image}")
    public String imgControl( @PathVariable String name, @PathVariable String image) throws Exception {
        WebSocketSession session = getSession(name);
        if (session != null) {
            session.sendMessage(new TextMessage(image));
        }

        return "webSocket";
    }

}
