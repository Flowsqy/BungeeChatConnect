package fr.flowsqy.bungeechatconnect;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class MessageQueueManager {

    private final Plugin plugin;
    // TODO Initialize queues
    private MessageQueue[] queues;

    public MessageQueueManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void subscribe(@NotNull Player player, @NotNull String format, @NotNull String message) {
        final AsyncMessageSender asyncMessageSender = new AsyncMessageSender();
        for (MessageQueue queue : queues) {
            asyncMessageSender.sendMessage(plugin, queue, player, format, message);
        }
    }

}
