package com.example.websockettest.controller;

import com.example.websockettest.handler.WebSocketHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

@Controller
public class MvcController {

    @GetMapping("/webSocket")
    public String webSocket() {
        return "webSocket";
    }

    public WebSocketSession getSession(String name) {
        HashMap<String, WebSocketSession> hashMap = WebSocketHandler.sessionMap;
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
