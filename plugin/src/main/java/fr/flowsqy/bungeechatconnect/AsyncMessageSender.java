package fr.flowsqy.bungeechatconnect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class AsyncMessageSender {

    public void sendMessage(@NotNull Plugin plugin, @NotNull Player player, @NotNull String format, @NotNull String message, @NotNull String[] servers) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new SendMessageRunnable(plugin, true, player, message, format, servers));
    }

}
