package fr.flowsqy.bungeechatconnect.protocol.message;

import fr.flowsqy.bungeechatconnect.event.BungeePlayerChatEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageSender {

    public void sendMessage(@NotNull BungeePlayerChatEvent event) {
        final String message = String.format(event.getFormat(), event.getDisplayName(), event.getMessage());
        for (Player player : event.getRecipient()) {
            player.sendMessage(message);
        }
    }

}
