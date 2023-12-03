package icu.ripley.loki.spigot.communication.model;

import lombok.Data;

@Data
public class SpigotServer {
    private String name;
    private String ip;
    private String port;

    public SpigotServer(String name, String ip, String port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }
}
