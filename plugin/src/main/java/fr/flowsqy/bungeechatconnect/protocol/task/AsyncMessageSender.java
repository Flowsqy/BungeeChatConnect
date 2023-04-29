package fr.flowsqy.bungeechatconnect.protocol.task;

import fr.flowsqy.bungeechatconnect.protocol.queue.MessageQueue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class AsyncMessageSender {

    public void sendMessage(@NotNull Plugin plugin, @NotNull MessageQueue queue, @NotNull Player player, @NotNull String format, @NotNull String message) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new SendMessageRunnable(queue, true, player, format, message));
    }

}
