package fr.flowsqy.bungeechatconnect.protocol.process;

import fr.flowsqy.bungeechatconnect.protocol.ChannelRegistry;
import fr.flowsqy.bungeechatconnect.protocol.message.MessageWriter;
import fr.flowsqy.bungeechatconnect.protocol.message.SendMessageData;
import fr.flowsqy.noqueuepluginmessage.api.NoQueuePluginMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class NetworkMessageSender {

    public void sendMessage(@NotNull Player player, @NotNull String[] serverNames, @NotNull SendMessageData sendMessageData) {
        final MessageWriter writer = new MessageWriter();
        final byte[] messageData;
        try {
            messageData = writer.write(sendMessageData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        NoQueuePluginMessage.sendPluginMessage(player, serverNames, ChannelRegistry.CHAT_CHANNEL, messageData);
    }

}
