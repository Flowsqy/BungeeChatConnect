package fr.flowsqy.bungeechatconnect.protocol.message;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record SendMessageData(@NotNull String name, @NotNull String displayName, @NotNull String format,
                              @NotNull String message, @NotNull List<PrepareMessageEvent.ExtraData> extraDataList) {

    @NotNull
    public static SendMessageData snapshot(PrepareMessageEvent event) {
        final Player sender = event.getPlayer();
        return new SendMessageData(sender.getName(), sender.getDisplayName(), event.getFormat(), event.getMessage(), event.getExtraDataList());
    }

}
