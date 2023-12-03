package icu.ripley.loki.spigot.communication.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import icu.ripley.loki.spigot.communication.model.SpigotEvent;
import icu.ripley.loki.spigot.events.SpigotStatusUpdateEvent;
import org.bukkit.plugin.PluginManager;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

public class ServerStatusEventHandler implements StompFrameHandler {

    private final ObjectMapper objectMapper;
    private final PluginManager pluginManager;

    public ServerStatusEventHandler(ObjectMapper objectMapper, PluginManager pluginManager) {
        this.objectMapper = objectMapper;
        this.pluginManager = pluginManager;
        System.out.println("ServerStatusEventHandler instance created.");
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return SpigotEvent.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        SpigotEvent event = (SpigotEvent) payload;

        System.out.println("spigotevent fired!!!");

        pluginManager.callEvent(new SpigotStatusUpdateEvent(event));
    }
}
