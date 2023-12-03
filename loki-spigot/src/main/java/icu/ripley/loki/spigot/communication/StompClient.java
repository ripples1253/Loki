package icu.ripley.loki.spigot.communication;

import lombok.Getter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StompClient {
    public StompSession session;

    private Logger logger;

    public StompClient(Logger logger) {
        this.logger = logger;
    }

    public void init(){
        this.session = connectToWebSocket();
    }

    private StompSession connectToWebSocket() {
        String url = "ws://127.0.0.1:8080/connect";
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandlerAdapter sessionHandler = new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession stompSession, StompHeaders connectedHeaders) {
                logger.info("Connected to WebSocket");
            }
        };

        return stompClient.connect(url, sessionHandler).completable().join();
    }

}
