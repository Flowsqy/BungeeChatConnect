package fr.flowsqy.bungeechatconnect;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class MessageReceiveListener implements PluginMessageListener {

    private final Plugin plugin;

    public MessageReceiveListener(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        AsyncMessageReceiver asyncMessageReceiver = new AsyncMessageReceiver();
        asyncMessageReceiver.receiveMessage(plugin, message);
    }
}
