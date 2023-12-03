package icu.ripley.loki.spigot;

import icu.ripley.loki.spigot.communication.StompClient;
import icu.ripley.loki.spigot.communication.WebsocketEventHandler;
import icu.ripley.loki.spigot.communication.model.SpigotEventType;
import icu.ripley.loki.spigot.communication.model.SpigotServer;
import icu.ripley.loki.spigot.listeners.SpigotUpdateListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Plugin extends JavaPlugin {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private StompClient stompClient;
    public SpigotServer server;
    public WebsocketEventHandler websocketEventHandler;

    @Override
    public void onEnable() {
        this.stompClient = new StompClient(getLogger());
        stompClient.init();

        server = new SpigotServer(getServer().getName(),
                getServer().getIp(),
                String.valueOf(getServer().getPort()));

        websocketEventHandler = new WebsocketEventHandler(stompClient.session,
                server,
                this);

        registerEvents();

        websocketEventHandler.sendServerStatus(SpigotEventType.STARTED);
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new SpigotUpdateListener(this),
                this);
    }

    @Override
    public void onDisable() {
        // This will be called when your plugin is disabled
        websocketEventHandler.sendServerStatus(SpigotEventType.STARTED);
        stompClient.session.disconnect();
        scheduler.shutdown();
    }
}
