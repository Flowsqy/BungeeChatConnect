package fr.flowsqy.bungeechatconnect;

import fr.flowsqy.bungeechatconnect.event.BungeePlayerChatEvent;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class MessageFinalizer {

    @NotNull
    public BungeePlayerChatEvent finalize(boolean async, ReceivedMessage receivedMessage) {
        final BungeePlayerChatEvent event = new BungeePlayerChatEvent(
                async,
                receivedMessage.getName(),
                receivedMessage.getDisplayName(),
                receivedMessage.getFormat(),
                receivedMessage.getMessage(),
                new HashSet<>(Bukkit.getOnlinePlayers()),
                receivedMessage.getExtraData()
        );
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }

}
