package icu.ripley.loki.spigot.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import icu.ripley.loki.spigot.Plugin;
import icu.ripley.loki.spigot.communication.handlers.ServerStatusEventHandler;
import icu.ripley.loki.spigot.communication.model.SpigotEvent;
import icu.ripley.loki.spigot.communication.model.SpigotEventType;
import icu.ripley.loki.spigot.communication.model.SpigotServer;
import org.springframework.messaging.simp.stomp.StompSession;

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
        session.subscribe("/events/spigotStatusUpdateEvent",
                new ServerStatusEventHandler(new ObjectMapper(), plugin.getServer().getPluginManager()));
    }
}
