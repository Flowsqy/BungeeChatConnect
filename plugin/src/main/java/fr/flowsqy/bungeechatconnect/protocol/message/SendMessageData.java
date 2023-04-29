package fr.flowsqy.bungeechatconnect.protocol.message;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SendMessageData {

    private final String name;
    private final String displayName;
    private final String format;
    private final String message;
    private final List<PrepareMessageEvent.ExtraData> extraDataList;

    public SendMessageData(@NotNull String name, @NotNull String displayName, @NotNull String format, @NotNull String message, @NotNull List<PrepareMessageEvent.ExtraData> extraDataList) {
        this.name = name;
        this.displayName = displayName;
        this.format = format;
        this.message = message;
        this.extraDataList = extraDataList;
    }

    @NotNull
    public static SendMessageData snapshot(PrepareMessageEvent event) {
        final Player sender = event.getPlayer();
        return new SendMessageData(sender.getName(), sender.getDisplayName(), event.getFormat(), event.getMessage(), event.getExtraDataList());
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getDisplayName() {
        return displayName;
    }

    @NotNull
    public String getFormat() {
        return format;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    @NotNull
    public List<PrepareMessageEvent.ExtraData> getExtraDataList() {
        return extraDataList;
    }

}
