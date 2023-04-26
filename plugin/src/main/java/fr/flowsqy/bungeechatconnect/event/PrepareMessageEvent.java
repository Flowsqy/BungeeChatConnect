package fr.flowsqy.bungeechatconnect.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the plugin is about to send a message through the network
 */
public class PrepareMessageEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancel;
    private final Player player;
    private String format;
    private String message;

    public PrepareMessageEvent(boolean isAsync, @NotNull Player player, @NotNull String format, @NotNull String message) {
        super(isAsync);
        this.player = player;
        this.format = format;
        this.message = message;
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
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
