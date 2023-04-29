package fr.flowsqy.bungeechatconnect.protocol.process;

import fr.flowsqy.bungeechatconnect.protocol.message.MessageWriter;
import fr.flowsqy.bungeechatconnect.protocol.message.SendMessageData;
import fr.flowsqy.bungeechatconnect.protocol.packet.PacketSender;
import fr.flowsqy.bungeechatconnect.protocol.packet.PacketWriter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class NetworkMessageSender {

    public void sendMessage(@NotNull Plugin plugin, @NotNull Player player, @NotNull String serverTo, @NotNull SendMessageData sendMessageData) {
        final MessageWriter writer = new MessageWriter();
        final byte[] messageData;
        try {
            messageData = writer.write(sendMessageData);
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
