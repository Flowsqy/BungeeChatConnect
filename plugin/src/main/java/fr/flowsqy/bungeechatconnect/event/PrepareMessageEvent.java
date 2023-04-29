package fr.flowsqy.bungeechatconnect.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Called when the plugin is about to send a message through the network
 */
public class PrepareMessageEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancel;
    private final Player player;
    private final String serverTo;
    private String format;
    private String message;
    private final List<ExtraData> extraDataList;

    public PrepareMessageEvent(boolean isAsync, @NotNull Player player, @NotNull String serverTo, @NotNull String format, @NotNull String message) {
        super(isAsync);
        this.player = player;
        this.serverTo = serverTo;
        this.format = format;
        this.message = message;
        extraDataList = new LinkedList<>();
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
    public Player getPlayer() {
        return player;
    }

    @NotNull
    public String getServerTo() {
        return serverTo;
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

    public void addExtraData(@NotNull String identifier, byte @NotNull [] data) {
        extraDataList.add(new ExtraData(identifier, data));
    }

    @NotNull
    public List<ExtraData> getExtraDataList() {
        return Collections.unmodifiableList(extraDataList);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public final static class ExtraData {

        private final String identifier;
        private final byte[] data;

        public ExtraData(@NotNull String identifier, byte @NotNull [] data) {
            this.identifier = identifier;
            this.data = data;
        }

        @NotNull
        public String getIdentifier() {
            return identifier;
        }

        public byte @NotNull [] getData() {
            return data;
        }
    }

}
