package fr.flowsqy.bungeechatconnect.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class Config {

    private YamlConfiguration configuration;

    public void load(ConfigLoader configLoader, JavaPlugin javaPlugin, String fileName) {
        configuration = configLoader.initFile(javaPlugin.getDataFolder(), Objects.requireNonNull(javaPlugin.getResource(fileName)), fileName);
    }

    public Map<String, Integer> getServersExpirationTimes(Logger logger) {
        final Map<String, Integer> serverExpirationTime = new HashMap<>();
        for (String server : configuration.getKeys(false)) {
            final String rawExpirationTime = configuration.getString(server);
            if (rawExpirationTime == null) {
                logger.warning(server + " does not specify any value");
                continue;
            }
            final int expirationTime;
            try {
                expirationTime = Integer.parseInt(rawExpirationTime);
            } catch (NumberFormatException e) {
                logger.warning("Awkward value for '" + server + "': '" + rawExpirationTime + "' is not a number");
                continue;
            }
            if (expirationTime <= 0) {
                logger.warning("Awkward value for '" + server + "': The value should be an integer superior to 0");
                continue;
            }
            serverExpirationTime.put(server, expirationTime);
        }
        return serverExpirationTime;
    }

}
