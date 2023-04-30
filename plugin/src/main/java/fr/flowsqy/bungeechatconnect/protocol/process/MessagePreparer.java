package fr.flowsqy.bungeechatconnect.protocol.process;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessagePreparer {

    @NotNull
    public PrepareMessageEvent prepare(boolean async, boolean cancelled, @NotNull Player player, @NotNull String serverTo, @NotNull String format, @NotNull String message) {
        final PrepareMessageEvent messageEvent = new PrepareMessageEvent(async, player, serverTo, format, message);
        messageEvent.setCancelled(cancelled);
        Bukkit.getPluginManager().callEvent(messageEvent);
        return messageEvent;
    }

}
