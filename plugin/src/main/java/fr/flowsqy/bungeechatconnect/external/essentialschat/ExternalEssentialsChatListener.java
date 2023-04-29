package fr.flowsqy.bungeechatconnect.external.essentialschat;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import net.essentialsx.api.v2.events.chat.GlobalChatEvent;
import net.essentialsx.api.v2.events.chat.LocalChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ExternalEssentialsChatListener implements Listener {

    // We can use a queue because PrepareMessageEvent events are called in the same order as the messages are sent
    private final Queue<Boolean> messageQueue;

    public ExternalEssentialsChatListener() {
        messageQueue = new ConcurrentLinkedQueue<>();
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onChat(LocalChatEvent event) {
        subscribe(true);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onChat(GlobalChatEvent event) {
        subscribe(false);
    }

    private void subscribe(boolean local) {
        messageQueue.offer(local);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    private void onPrepare(PrepareMessageEvent event) {
        final boolean local = Objects.requireNonNull(messageQueue.poll());
        if (local) {
            event.setCancelled(true);
        }
    }

}
