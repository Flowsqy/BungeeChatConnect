package fr.flowsqy.bungeechatconnect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public class PlayerCountQuery implements PluginMessageListener {

    private final Plugin plugin;
    private final String server;
    private final Runnable callBack;
    private volatile boolean havePlayer;

    public PlayerCountQuery(@NotNull Plugin plugin, @NotNull String server, @Nullable Runnable callBack) {
        this.plugin = plugin;
        this.server = server;
        this.callBack = callBack;
        this.havePlayer = false;
    }

    public void register() {
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
    }

    public void unregister() {
        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, "BungeeCord", this);
    }

    public boolean havePlayer() {
        return havePlayer;
    }

    public void query(@NotNull Player player) {
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
        final int playerCount;
        try {
            playerCount = readMessage(message);
        } catch (IOException ignored) {
            return;
        }
        if (playerCount < 0) {
            return;
        }
        actualize(playerCount);
    }

    private int readMessage(byte[] message) throws IOException {
        final DataInputStream inDataStream = new DataInputStream(new ByteArrayInputStream(message));
        final String subChannel = inDataStream.readUTF();
        if (!subChannel.equals("PlayerCount")) {
            return -1;
        }
        final String server = inDataStream.readUTF();
        if (!server.equals(this.server)) {
            return -1;
        }
        return inDataStream.readInt();
    }

    private void actualize(int playerCount) {
        this.havePlayer = playerCount > 0;
        if (callBack != null) {
            callBack.run();
        }
    }
}
