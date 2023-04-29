package fr.flowsqy.bungeechatconnect.listener;

import fr.flowsqy.bungeechatconnect.protocol.queue.MessageQueueManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerChatListener implements Listener {

    private final MessageQueueManager queueManager;

    public PlayerChatListener(@NotNull MessageQueueManager queueManager) {
        this.queueManager = queueManager;
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerChat(AsyncPlayerChatEvent event) {
        queueManager.subscribe(event.getPlayer(), event.getFormat(), event.getMessage());
    }

}
