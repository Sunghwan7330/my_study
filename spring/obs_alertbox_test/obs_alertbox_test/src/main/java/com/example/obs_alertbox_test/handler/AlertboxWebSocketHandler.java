package com.example.obs_alertbox_test.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;

@Log4j2
public class AlertboxWebSocketHandler extends TextWebSocketHandler {
    public static HashMap<String, WebSocketSession> sessionMap;

    public AlertboxWebSocketHandler() {
        sessionMap = new HashMap<String, WebSocketSession>();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        log.debug("session : " + session + ", message : " + message.getPayload());
        String msg = message.getPayload();
        String sprit_msg[] = msg.split(" ");
        if (sprit_msg.length == 2 && sprit_msg[0].equals("connection")) {
            log.debug("name : " + sprit_msg[1]);
            sessionMap.put(sprit_msg[1], session);
        }
        else {
            session.sendMessage(new TextMessage("Hello " + message.getPayload()));
        }
    }
}
