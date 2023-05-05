package fr.flowsqy.bungeechatconnect.protocol.process;

import fr.flowsqy.bungeechatconnect.event.BungeePlayerChatEvent;
import fr.flowsqy.bungeechatconnect.protocol.message.ReceivedMessage;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class MessageFinalizer {

    @NotNull
    public BungeePlayerChatEvent finalize(boolean async, ReceivedMessage receivedMessage) {
        final BungeePlayerChatEvent event = new BungeePlayerChatEvent(
                async,
                receivedMessage.name(),
                receivedMessage.displayName(),
                receivedMessage.format(),
                receivedMessage.message(),
                new HashSet<>(Bukkit.getOnlinePlayers()),
                receivedMessage.extraData()
        );
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }

}
