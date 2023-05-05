package fr.flowsqy.bungeechatconnect.listener;

import fr.flowsqy.bungeechatconnect.protocol.task.AsyncMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PlayerChatListener implements Listener {

    private final Plugin plugin;
    private final String[] serverNames;

    public PlayerChatListener(@NotNull Plugin plugin, @NotNull String[] serverNames) {
        this.plugin = plugin;
        this.serverNames = serverNames;
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerChat(AsyncPlayerChatEvent event) {
        final AsyncMessageSender messageSender = new AsyncMessageSender();
        messageSender.sendMessage(plugin, event.isCancelled(), !Bukkit.isPrimaryThread(), serverNames, event.getPlayer(), event.getFormat(), event.getMessage());
    }

}
