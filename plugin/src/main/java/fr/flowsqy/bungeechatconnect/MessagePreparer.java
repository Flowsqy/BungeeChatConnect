package fr.flowsqy.bungeechatconnect;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessagePreparer {

    public PrepareMessageEvent prepare(boolean sync, @NotNull Player player, @NotNull String message, @NotNull String format) {
        final PrepareMessageEvent messageEvent = new PrepareMessageEvent(sync, player, message, format);
        Bukkit.getPluginManager().callEvent(messageEvent);
        return messageEvent;
    }

}
