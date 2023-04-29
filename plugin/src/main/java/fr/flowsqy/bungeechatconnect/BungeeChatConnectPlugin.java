package fr.flowsqy.bungeechatconnect;

import fr.flowsqy.bungeechatconnect.config.Config;
import fr.flowsqy.bungeechatconnect.config.ConfigLoader;
import fr.flowsqy.bungeechatconnect.listener.MessageReceiveListener;
import fr.flowsqy.bungeechatconnect.listener.PlayerChatListener;
import fr.flowsqy.bungeechatconnect.protocol.NetworkRegistry;
import fr.flowsqy.bungeechatconnect.protocol.queue.MessageQueueManager;
import fr.flowsqy.bungeechatconnect.protocol.queue.MessageQueuesProvider;
import fr.flowsqy.bungeechatconnect.protocol.queue.servers.ServersIdentifier;
import fr.flowsqy.bungeechatconnect.protocol.queue.servers.ServersQuery;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class BungeeChatConnectPlugin extends JavaPlugin {

    private MessageReceiveListener messageReceiveListener;
    private MessageQueuesProvider messageQueuesProvider;

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
        final Map<String, Integer> serverExpirationTimes = config.getServersExpirationTimes(logger);

        final NetworkRegistry channelRegistry = new NetworkRegistry();
        channelRegistry.registerOut(this);

        final ServersIdentifier serversIdentifier = new ServersIdentifier(new ServersQuery(this, serverExpirationTimes.keySet().toArray(new String[0])));
        messageQueuesProvider = new MessageQueuesProvider(this, serverExpirationTimes, serversIdentifier);
        final MessageQueueManager messageQueueManager = new MessageQueueManager(this, messageQueuesProvider);

        messageReceiveListener = new MessageReceiveListener(this);
        messageReceiveListener.register();

        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(messageQueueManager), this);
    }

    @Override
    public void onDisable() {
        messageReceiveListener.unregister();
        messageQueuesProvider.unregister();

        final NetworkRegistry channelRegistry = new NetworkRegistry();
        channelRegistry.unregisterOut(this);
    }
}
