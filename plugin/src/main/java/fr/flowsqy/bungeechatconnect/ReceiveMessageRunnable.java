package fr.flowsqy.bungeechatconnect;

import fr.flowsqy.bungeechatconnect.event.BungeePlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ReceiveMessageRunnable implements Runnable {

    private final boolean async;
    private final byte[] packetData;

    public ReceiveMessageRunnable(boolean async, byte @NotNull [] packetData) {
        this.async = async;
        this.packetData = packetData;
    }

    @Override
    public void run() {
        final PacketReader packetReader = new PacketReader();
        final PacketReader.Result packetResult;
        try {
            packetResult = packetReader.read(packetData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!packetResult.isValid()) {
            return;
        }
        final MessageReader messageReader = new MessageReader();
        final ReceivedMessage receivedMessage;
        try {
            receivedMessage = messageReader.read(packetResult.getMessageData());
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
