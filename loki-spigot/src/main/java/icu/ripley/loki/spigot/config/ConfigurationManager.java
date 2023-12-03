package icu.ripley.loki.spigot.config;

import icu.ripley.loki.spigot.Plugin;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigurationManager {

    public YamlConfiguration config;

    private Plugin plugin;

    public ConfigurationManager(Plugin plugin) {
        this.plugin = plugin;

        loadConfig();
    }

    private void loadConfig() {
        File configFile = new File(plugin.getServer().getWorldContainer(), "Loki.yml");
        System.out.println("grabbed Loki.yml from world container");
        config = YamlConfiguration.loadConfiguration(configFile);
    }

}
