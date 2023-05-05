package fr.flowsqy.bungeechatconnect.protocol.task;

import fr.flowsqy.bungeechatconnect.event.BungeePlayerChatEvent;
import fr.flowsqy.bungeechatconnect.protocol.message.MessageReader;
import fr.flowsqy.bungeechatconnect.protocol.message.MessageSender;
import fr.flowsqy.bungeechatconnect.protocol.message.ReceivedMessage;
import fr.flowsqy.bungeechatconnect.protocol.process.MessageFinalizer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ReceiveMessageRunnable implements Runnable {

    private final boolean async;
    private final byte[] data;

    public ReceiveMessageRunnable(boolean async, byte @NotNull [] data) {
        this.async = async;
        this.data = data;
    }

    @Override
    public void run() {
        final MessageReader messageReader = new MessageReader();
        final ReceivedMessage receivedMessage;
        try {
            receivedMessage = messageReader.read(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final MessageFinalizer messageFinalizer = new MessageFinalizer();
        final BungeePlayerChatEvent bungeePlayerChatEvent = messageFinalizer.finalize(async, receivedMessage);
        if (bungeePlayerChatEvent.isCancelled()) {
            return;
        }
        final MessageSender messageSender = new MessageSender();
        messageSender.sendMessage(bungeePlayerChatEvent);
    }
}
