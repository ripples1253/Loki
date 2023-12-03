package icu.ripley.loki.spigot.communication;

import icu.ripley.loki.spigot.config.ConfigurationManager;
import lombok.Getter;
import org.springframework.lang.Nullable;
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
    private ConfigurationManager configurationManager;

    public StompClient(Logger logger) {
        this.logger = logger;
    }

    public void init(ConfigurationManager configurationManager){
        // note to self: GET THE ORDER OF UR FUCKING VARIABLES RIGHT YOU RETARDED FUCK
        this.configurationManager = configurationManager;
        this.session = connectToWebSocket();
    }

    private StompSession connectToWebSocket() {
        String url = configurationManager.config.getString("microservice-url");
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandlerAdapter sessionHandler = new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession stompSession, StompHeaders connectedHeaders) {
                logger.info("Loki Service Manager > Connected to spigot-server-microservice!");
            }

            @Override
            public void handleFrame(StompHeaders headers, @Nullable Object payload) {
                System.out.println(headers.toString());
                System.out.println(Object.class.getName());
            }
        };

        return stompClient.connect(url, sessionHandler).completable().join();
    }

}
