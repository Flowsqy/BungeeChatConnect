package fr.flowsqy.bungeechatconnect.protocol.queue;

import fr.flowsqy.bungeechatconnect.protocol.queue.servers.ServersIdentifier;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MessageQueuesProvider {

    private Plugin plugin;
    private Map<String, Integer> serverExpirationTimes;
    private ServersIdentifier serversIdentifier;
    private boolean initialized;
    private MessageQueue[] queues;

    public MessageQueuesProvider(@NotNull Plugin plugin, @NotNull Map<String, Integer> serverExpirationTimes, @NotNull ServersIdentifier serversIdentifier) {
        this.plugin = plugin;
        this.serverExpirationTimes = serverExpirationTimes;
        this.serversIdentifier = serversIdentifier;
        initialized = false;
    }

    private void register() {
        for (MessageQueue queue : queues) {
            queue.register();
        }
    }

    public void unregister() {
        for (MessageQueue queue : queues) {
            queue.unregister();
        }
    }

    @NotNull
    public MessageQueue[] get(Player player) {
        if (initialized) {
            return queues;
        }
        synchronized (this) {
            if (initialized) {
                return queues;
            }
            final MessageQueue[] queues = query(player);
            initialize(queues);
            return queues;
        }
    }

    @NotNull
    private MessageQueue[] query(Player player) {
        final String[] servers = serversIdentifier.query(player);
        final List<MessageQueue> queues = new LinkedList<>();
        for (String server : servers) {
            final Integer expirationTime = serverExpirationTimes.remove(server);
            if (expirationTime != null) {
                queues.add(new MessageQueue(plugin, expirationTime, server));
            }
        }
        return queues.toArray(new MessageQueue[0]);
    }

    private void initialize(@NotNull MessageQueue[] queues) {
        this.queues = queues;
        register();
        plugin = null;
        serverExpirationTimes = null;
        serversIdentifier = null;
        initialized = true;
    }

}
