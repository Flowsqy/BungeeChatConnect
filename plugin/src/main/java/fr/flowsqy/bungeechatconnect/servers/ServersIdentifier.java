package fr.flowsqy.bungeechatconnect.servers;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ServersIdentifier {

    private String[] servers;
    private ServersQuery serversQuery;
    private volatile boolean initialized;

    public ServersIdentifier(@NotNull ServersQuery serversQuery) {
        this.servers = null;
        this.serversQuery = serversQuery;
    }

    @NotNull
    public String[] query(Player player) {
        if (initialized) {
            return servers;
        }
        synchronized (this) {
            if (initialized) {
                return servers;
            }
            try {
                servers = serversQuery.query(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            serversQuery = null;
            initialized = true;
            return servers;
        }
    }

}
