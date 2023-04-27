package fr.flowsqy.bungeechatconnect.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public class BungeePlayerChatEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancel;
    private final String name;
    private final String displayName;
    private String format;
    private String message;
    private final Map<String, byte[]> extraData;

    public BungeePlayerChatEvent(boolean isAsync, @NotNull String name, @NotNull String displayName, @NotNull String format, @NotNull String message, @NotNull Map<String, byte[]> extraData) {
        super(isAsync);
        this.name = name;
        this.displayName = displayName;
        this.format = format;
        this.message = message;
        this.extraData = Collections.unmodifiableMap(extraData);
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
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

    public void setFormat(@NotNull String format) {
        this.format = format;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NotNull String message) {
        this.message = message;
    }

    @NotNull
    public Map<String, byte[]> getExtraData() {
        return extraData;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}