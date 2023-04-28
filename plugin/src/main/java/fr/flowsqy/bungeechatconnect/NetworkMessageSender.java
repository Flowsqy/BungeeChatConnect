package fr.flowsqy.bungeechatconnect;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class NetworkMessageSender {

    public void sendMessage(@NotNull Plugin plugin, @NotNull Player player, @NotNull String serverTo, @NotNull SendMessageData sendMessageData) {
        final MessageWriter writer = new MessageWriter();
        final byte[] messageData;
        try {
            messageData = writer.write(player.getName(), player.getDisplayName(), sendMessageData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final PacketWriter packetWriter = new PacketWriter();
        final PacketSender packetSender = new PacketSender();
        final byte[] packetData;
        try {
            packetData = packetWriter.write(serverTo, messageData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        packetSender.send(plugin, player, packetData);
    }

}
