package fr.flowsqy.bungeechatconnect;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessagePreparer {

    @NotNull
    public PrepareMessageEvent prepare(boolean async, @NotNull Player player, @NotNull String message, @NotNull String format) {
        final PrepareMessageEvent messageEvent = new PrepareMessageEvent(async, player, message, format);
        Bukkit.getPluginManager().callEvent(messageEvent);
        return messageEvent;
    }

}
