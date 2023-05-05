package fr.flowsqy.bungeechatconnect;

import fr.flowsqy.bungeechatconnect.config.Config;
import fr.flowsqy.bungeechatconnect.config.ConfigLoader;
import fr.flowsqy.bungeechatconnect.external.ExternalManager;
import fr.flowsqy.bungeechatconnect.listener.MessageReceiveListener;
import fr.flowsqy.bungeechatconnect.listener.PlayerChatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class BungeeChatConnectPlugin extends JavaPlugin {

    private MessageReceiveListener messageReceiveListener;

    @Override
    public void onEnable() {

        final Logger logger = getLogger();
        final File dataFolder = getDataFolder();
        final ConfigLoader configLoader = new ConfigLoader();

        if (!configLoader.checkDataFolder(dataFolder)) {
            logger.log(Level.WARNING, "Can not write in the directory : " + dataFolder.getAbsolutePath());
            logger.log(Level.WARNING, "Disable the plugin");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        final Config config = new Config();
        config.load(configLoader, this, "config.yml");
        final List<String> servers = config.getServers();
        if (servers.isEmpty()) {
            return;
        }
        final String[] serverNames = servers.toArray(new String[0]);

        final ExternalManager externalManager = new ExternalManager();
        externalManager.load(this);

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerChatListener(this, serverNames), this);
        pluginManager.registerEvents(new MessageReceiveListener(this), this);
    }

}
