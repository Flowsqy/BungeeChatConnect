package fr.flowsqy.bungeechatconnect.server;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class ServersQuery implements PluginMessageListener {

    private final Plugin plugin;
    private String[] servers;

    public ServersQuery(@NotNull Plugin plugin, @NotNull String[] servers) {
        this.plugin = plugin;
        this.servers = servers;
    }

    @NotNull
    public String[] query(@NotNull Player player) throws IOException {
        synchronized (this) {
            register();
            final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            final DataOutputStream outDataStream = new DataOutputStream(outByteStream);
            outDataStream.writeUTF("GetServers");
            player.sendPluginMessage(plugin, "BungeeCord", outByteStream.toByteArray());
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return servers;
        }
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        final DataInputStream inDataStream = new DataInputStream(new ByteArrayInputStream(message));
        final String serverList;
        try {
            final String subChannel = inDataStream.readUTF();
            if (!subChannel.equals("GetServers")) {
                return;
            }
            serverList = inDataStream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        synchronized (this) {
            servers = process(serverList);
            unregister();
            this.notify();
        }
    }

    private String[] process(@NotNull String serverList) {
        final Set<String> configuredServers = new HashSet<>(Arrays.asList(serverList.split(", ")));
        final LinkedList<String> availableServers = new LinkedList<>();
        for (String server : servers) {
            if (configuredServers.remove(server)) {
                availableServers.add(server);
            }
        }
        return availableServers.toArray(new String[availableServers.size()]);
    }

    private void register() {
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
    }

    private void unregister() {
        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, "BungeeCord", this);
    }
}
