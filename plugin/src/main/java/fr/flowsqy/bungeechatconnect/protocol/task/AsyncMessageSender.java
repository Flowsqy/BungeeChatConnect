package fr.flowsqy.bungeechatconnect.protocol.task;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import fr.flowsqy.bungeechatconnect.protocol.message.SendMessageData;
import fr.flowsqy.bungeechatconnect.protocol.process.MessagePreparer;
import fr.flowsqy.bungeechatconnect.protocol.process.NetworkMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class AsyncMessageSender {

    public void sendMessage(@NotNull Plugin plugin, boolean cancelled, boolean async, @NotNull String[] serverNames, @NotNull Player player, @NotNull String format, @NotNull String message) {
        final MessagePreparer preparer = new MessagePreparer();
        final PrepareMessageEvent event = preparer.prepare(async, cancelled, player, serverNames, format, message);
        if (event.isCancelled()) {
            return;
        }
        // Removing the async call will make the server send packets in the correct order
        // Which is useless as the receiver does not receive packets in the same order they were sent
        final NetworkMessageSender networkMessageSender = new NetworkMessageSender();
        final SendMessageData sendMessageData = SendMessageData.snapshot(event);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> networkMessageSender.sendMessage(player, serverNames, sendMessageData));
    }

}
