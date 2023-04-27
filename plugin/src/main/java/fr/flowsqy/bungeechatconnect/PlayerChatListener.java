package fr.flowsqy.bungeechatconnect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PlayerChatListener {

    private final Plugin plugin;
    private final String[] servers;

    public PlayerChatListener(@NotNull Plugin plugin, @NotNull String[] servers) {
        this.plugin = plugin;
        this.servers = servers;
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerChat(AsyncPlayerChatEvent event) {
        final AsyncMessageSender asyncMessageSender = new AsyncMessageSender();
        asyncMessageSender.sendMessage(plugin, event.getPlayer(), event.getFormat(), event.getMessage(), servers);
    }

}
