package fr.flowsqy.bungeechatconnect.external.essentialschat;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import net.essentialsx.api.v2.events.chat.GlobalChatEvent;
import net.essentialsx.api.v2.events.chat.LocalChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ExternalEssentialsChatListener implements Listener {

    // We can use a queue because PrepareMessageEvent events are called in the same order as the messages are sent
    private final Map<UUID, Queue<Boolean>> queues;

    public ExternalEssentialsChatListener() {
        queues = new ConcurrentHashMap<>();
    }

    public void enable() {
        // Prevent null queues for already connected players
        for (Player players : Bukkit.getOnlinePlayers()) {
            register(players);
        }
    }

    private void register(Player p) {
        queues.put(p.getUniqueId(), new ConcurrentLinkedQueue<>());
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    private void onJoin(PlayerJoinEvent event) {
        register(event.getPlayer());
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    private void onLeave(PlayerQuitEvent event) {
        queues.remove(event.getPlayer().getUniqueId());
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onChat(LocalChatEvent event) {
        subscribe(event.getPlayer().getUniqueId(), true);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onChat(GlobalChatEvent event) {
        subscribe(event.getPlayer().getUniqueId(), false);
    }

    private void subscribe(UUID player, boolean local) {
        Objects.requireNonNull(queues.get(player)).offer(local);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    private void onPrepare(PrepareMessageEvent event) {
        final Queue<Boolean> messageQueue = Objects.requireNonNull(queues.get(event.getPlayer().getUniqueId()));
        final boolean local = Objects.requireNonNull(messageQueue.poll());
        if (local) {
            event.setCancelled(true);
        }
    }

}
