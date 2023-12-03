package icu.ripley.loki.spigot.listeners;

import icu.ripley.loki.spigot.Plugin;
import icu.ripley.loki.spigot.communication.model.SpigotEvent;
import icu.ripley.loki.spigot.events.SpigotStatusUpdateEvent;
import icu.ripley.loki.spigot.utilities.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SpigotUpdateListener implements Listener {

    private final Plugin plugin;

    public SpigotUpdateListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void handleStatusUpdate(SpigotStatusUpdateEvent event){
        SpigotEvent spigotEvent = event.getSpigotEvent();

        plugin.getServer().broadcast(
                CC.translate("&c&lLoki Spigot Events > &9"
                        + spigotEvent.getServer()
                        + "&c has changed status to "
                        + spigotEvent.getEventType().toString()),
                ""
        );
    }

}
