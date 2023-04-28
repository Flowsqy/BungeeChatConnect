package fr.flowsqy.bungeechatconnect;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class QueueManager {

    private final Plugin plugin;
    // TODO Initialize queues
    private Queue[] queues;

    public QueueManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void subscribe(@NotNull Player player, @NotNull String format, @NotNull String message) {
        final AsyncMessageSender asyncMessageSender = new AsyncMessageSender();
        for (Queue queue : queues) {
            asyncMessageSender.sendMessage(plugin, queue, player, format, message);
        }
    }

}
