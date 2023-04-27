package fr.flowsqy.bungeechatconnect;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ReceivedMessage {

    private final String name;
    private final String displayName;
    private final String format;
    private final String message;
    private final Map<String, byte[]> extraData;

    public ReceivedMessage(@NotNull String name, @NotNull String displayName, @NotNull String format, @NotNull String message, @NotNull Map<String, byte[]> extraData) {
        this.name = name;
        this.displayName = displayName;
        this.format = format;
        this.message = message;
        this.extraData = extraData;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFormat() {
        return format;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, byte[]> getExtraData() {
        return extraData;
    }
}
