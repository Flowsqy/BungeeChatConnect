package fr.flowsqy.bungeechatconnect.protocol.task;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import fr.flowsqy.bungeechatconnect.protocol.message.SendMessageData;
import fr.flowsqy.bungeechatconnect.protocol.process.MessagePreparer;
import fr.flowsqy.bungeechatconnect.protocol.queue.MessageQueue;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SendMessageRunnable implements Runnable {

    private final MessageQueue queue;
    private final boolean async;
    private final Player player;
    private final String format;
    private final String message;

    public SendMessageRunnable(@NotNull MessageQueue queue, boolean async, @NotNull Player player, @NotNull String format, @NotNull String message) {
        this.queue = queue;
        this.async = async;
        this.player = player;
        this.format = format;
        this.message = message;
    }

    @Override
    public void run() {
        final MessagePreparer preparer = new MessagePreparer();
        final PrepareMessageEvent event = preparer.prepare(async, player, queue.getServer(), format, message);
        if (event.isCancelled()) {
            return;
        }
        queue.subscribe(player, SendMessageData.snapshot(event));
    }
}
