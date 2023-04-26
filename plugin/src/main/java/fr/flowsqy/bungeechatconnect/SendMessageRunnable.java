package fr.flowsqy.bungeechatconnect;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SendMessageRunnable implements Runnable {

    private final Plugin plugin;
    private final boolean async;
    private final Player player;
    private final String message;
    private final String format;
    private final String[] servers;

    public SendMessageRunnable(@NotNull Plugin plugin, boolean async, @NotNull Player player, @NotNull String message, @NotNull String format, @NotNull String[] servers) {
        this.plugin = plugin;
        this.async = async;
        this.player = player;
        this.message = message;
        this.format = format;
        this.servers = servers;
    }

    @Override
    public void run() {
        final MessagePreparer preparer = new MessagePreparer();
        final PrepareMessageEvent event = preparer.prepare(async, player, message, format);
        if (event.isCancelled()) {
            return;
        }
        final MessageWriter writer = new MessageWriter();
        final byte[] messageData;
        try {
            messageData = writer.write(
                    player.getName(),
                    player.getDisplayName(),
                    event.getFormat(),
                    event.getMessage(),
                    event.getExtraDataList()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final PacketWriter packetWriter = new PacketWriter();
        final PacketSender packetSender = new PacketSender();
        for (String server : servers) {
            final byte[] packetData;
            try {
                packetData = packetWriter.write(server, messageData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            packetSender.send(plugin, player, packetData);
        }
    }
}
