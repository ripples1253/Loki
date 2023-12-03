package icu.ripley.loki.spigot.communication.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SpigotEvent {
    private SpigotServer server;
    private SpigotEventType eventType;

    @JsonCreator
    public SpigotEvent(@JsonProperty("eventData") SpigotServer server,
                       @JsonProperty("eventType") SpigotEventType eventType) {
        this.eventType = eventType;
        this.server = server;
    }
}
