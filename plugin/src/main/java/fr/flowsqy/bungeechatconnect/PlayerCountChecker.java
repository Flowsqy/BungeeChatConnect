package fr.flowsqy.bungeechatconnect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class PlayerCountChecker implements PluginMessageListener {

    private final String server;
    private final int expirationTime;
    private volatile boolean havePlayer;
    private volatile long lastCheck;

    public PlayerCountChecker(String server, int expirationTime) {
        this.server = server;
        this.expirationTime = expirationTime;
        this.havePlayer = false;
        this.lastCheck = 0;
    }

    public void register(@NotNull Plugin plugin) {
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
    }

    public void unregister(@NotNull Plugin plugin) {
        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, "BungeeCord", this);
    }

    public boolean havePlayer(@NotNull Plugin plugin, @NotNull Player player) {
        if (System.currentTimeMillis() - lastCheck > expirationTime) {
            queryPlayerCount(plugin, player);
            return false;
        }
        return havePlayer;
    }

    private void queryPlayerCount(@NotNull Plugin plugin, @NotNull Player player) {
        final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        final DataOutputStream outDataStream = new DataOutputStream(outByteStream);
        try {
            outDataStream.writeUTF("PlayerCount");
            outDataStream.writeUTF(server);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        player.sendPluginMessage(plugin, "BungeeCord", outByteStream.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        final DataInputStream inDataStream = new DataInputStream(new ByteArrayInputStream(message));
        try {
            final String subChannel = inDataStream.readUTF();
            if (!subChannel.equals("PlayerCount")) {
                return;
            }
            final String server = inDataStream.readUTF();
            if (!server.equals(this.server)) {
                return;
            }
            final int playerCount = inDataStream.readInt();
            actualize(playerCount);
        } catch (IOException ignored) {
        }
    }

    private void actualize(int playerCount) {
        this.havePlayer = playerCount > 0;
        this.lastCheck = System.currentTimeMillis();
    }
}
