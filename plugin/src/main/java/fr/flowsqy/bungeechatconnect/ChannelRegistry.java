package fr.flowsqy.bungeechatconnect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.jetbrains.annotations.NotNull;

public class ChannelRegistry {

    public final static String CHANNEL = "BungeeChatConnect";

    public void registerChannel(@NotNull Plugin plugin) {
        final Messenger messenger = Bukkit.getMessenger();
        messenger.registerOutgoingPluginChannel(plugin, "BungeeCord");
        messenger.registerIncomingPluginChannel(plugin, "BungeeCord", new MessageReceiveListener(plugin));
    }

    public void unregisterChannel(@NotNull Plugin plugin) {
        final Messenger messenger = Bukkit.getMessenger();
        messenger.unregisterOutgoingPluginChannel(plugin, "BungeeCord");
        messenger.unregisterIncomingPluginChannel(plugin, "BungeeCord");
    }

}
