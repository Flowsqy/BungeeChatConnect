package fr.flowsqy.bungeechatconnect.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class Config {

    private YamlConfiguration configuration;

    public void load(@NotNull ConfigLoader configLoader, @NotNull JavaPlugin javaPlugin, @NotNull String fileName) {
        configuration = configLoader.initFile(javaPlugin.getDataFolder(), Objects.requireNonNull(javaPlugin.getResource(fileName)), fileName);
    }

    public List<String> getServers() {
        return configuration.getStringList("servers");
    }

}
