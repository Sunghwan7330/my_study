package com.example.websockettest.controller;

import com.example.websockettest.handler.WebSocketHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        if(!hashMap.containsKey(name)) {
            return null;

        }
        return hashMap.get("123");
    }

    @GetMapping("/img1")
    public String img1() throws Exception {
        WebSocketSession session = getSession("123");
        if (session != null) {
            session.sendMessage(new TextMessage("./images/muzi.gif"));
        }

        return "webSocket";
    }
    @GetMapping("/img2")
    public String img2() throws Exception {
        WebSocketSession session = getSession("123");
        if (session != null) {
            session.sendMessage(new TextMessage("./images/ryon.png"));
        }

        return "webSocket";
    }
    @GetMapping("/img3")
    public String img3() throws Exception {
        WebSocketSession session = getSession("123");
        if (session != null) {
            session.sendMessage(new TextMessage("./images/tube.png"));
        }

        return "webSocket";
    }
}
