package icu.ripley.loki.spigot.events;

import icu.ripley.loki.spigot.communication.model.SpigotEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpigotStatusUpdateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final SpigotEvent event;

    public SpigotStatusUpdateEvent(SpigotEvent event) {
        this.event = event;
    }

    public SpigotEvent getSpigotEvent() {
        return event;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
