package icu.ripley.loki.spigot.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import icu.ripley.loki.spigot.Plugin;
import icu.ripley.loki.spigot.communication.handlers.ServerStatusEventHandler;
import icu.ripley.loki.spigot.communication.model.SpigotEvent;
import icu.ripley.loki.spigot.communication.model.SpigotEventType;
import icu.ripley.loki.spigot.communication.model.SpigotServer;
import icu.ripley.loki.spigot.events.SpigotStatusUpdateEvent;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import java.lang.reflect.Type;

public class WebsocketEventHandler {

    private final StompSession session;
    private final SpigotServer server;
    private final Plugin plugin;

    public WebsocketEventHandler(StompSession session, SpigotServer server, Plugin plugin) {
        this.session = session;
        this.server = server;
        this.plugin = plugin;

        System.out.println("creating new WebsocketEventHandler");

        createHandlers();
    }

    public void sendServerStatus(SpigotEventType eventType){
        session.send("/app/spigot/updateStatus", new SpigotEvent(server, eventType));
        System.out.println("updating server status - " + eventType);
    }

    private void createHandlers(){
        System.out.println("creating handlers for spigotStatusUpdateEvent");
        session.subscribe("/event/spigotStatusUpdateEvent",
                new ServerStatusEventHandler(new ObjectMapper(), plugin.getServer().getPluginManager()));

        session.subscribe("/event/spigotStatusUpdateEvent", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Object.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received status update frame!");
                SpigotEvent event = new ObjectMapper().convertValue(payload, SpigotEvent.class);

                System.out.println("spigotevent fired!!!");

                plugin.getServer().getPluginManager().callEvent(
                        new SpigotStatusUpdateEvent(event));
            }
        });
    }
}
