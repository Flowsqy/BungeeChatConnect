package fr.flowsqy.bungeechatconnect.protocol.queue;

import fr.flowsqy.bungeechatconnect.protocol.task.AsyncMessageSender;
import fr.flowsqy.bungeechatconnect.protocol.task.AsyncTaskQueue;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class MessageQueueManager {

    private final Plugin plugin;
    private final MessageQueuesProvider messageQueuesProvider;
    private final AsyncTaskQueue asyncTaskQueue;

    public MessageQueueManager(Plugin plugin, MessageQueuesProvider messageQueuesProvider) {
        this.plugin = plugin;
        this.messageQueuesProvider = messageQueuesProvider;
        this.asyncTaskQueue = new AsyncTaskQueue(plugin);
    }

    public void subscribe(@NotNull Player player, @NotNull String format, @NotNull String message) {
        asyncTaskQueue.subscribe(() -> asyncSubscribe(player, format, message));
    }

    private void asyncSubscribe(@NotNull Player player, @NotNull String format, @NotNull String message) {
        final AsyncMessageSender asyncMessageSender = new AsyncMessageSender();
        for (MessageQueue queue : messageQueuesProvider.get(player)) {
            asyncMessageSender.sendMessage(plugin, true, queue, player, format, message);
        }
    }

}
