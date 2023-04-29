package fr.flowsqy.bungeechatconnect.protocol.queue;

import fr.flowsqy.bungeechatconnect.protocol.task.AsyncMessageSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class MessageQueueManager {

    private final Plugin plugin;
    private final MessageQueuesProvider messageQueuesProvider;

    public MessageQueueManager(Plugin plugin, MessageQueuesProvider messageQueuesProvider) {
        this.plugin = plugin;
        this.messageQueuesProvider = messageQueuesProvider;
    }

    public void subscribe(@NotNull Player player, @NotNull String format, @NotNull String message) {
        final AsyncMessageSender asyncMessageSender = new AsyncMessageSender();
        for (MessageQueue queue : messageQueuesProvider.get(player)) {
            asyncMessageSender.sendMessage(plugin, queue, player, format, message);
        }
    }

}
