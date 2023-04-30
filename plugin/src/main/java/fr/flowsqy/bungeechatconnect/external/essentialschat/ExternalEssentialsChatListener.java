package fr.flowsqy.bungeechatconnect.external.essentialschat;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import net.essentialsx.api.v2.ChatType;
import net.essentialsx.api.v2.events.chat.GlobalChatEvent;
import net.essentialsx.api.v2.events.chat.LocalChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ExternalEssentialsChatListener implements Listener {

    // We can use a queue because PrepareMessageEvent events are called in the same order as the messages are sent
    private final Queue<ChatType> messageQueue;

    public ExternalEssentialsChatListener() {
        messageQueue = new ConcurrentLinkedQueue<>();
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    private void onChat(LocalChatEvent event) {
        subscribe(event.getChatType());
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    private void onChat(GlobalChatEvent event) {
        subscribe(event.getChatType());
    }

    private void subscribe(ChatType chatType) {
        messageQueue.offer(chatType);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    private void onPrepare(PrepareMessageEvent event) {
        final ChatType chatType = Objects.requireNonNull(messageQueue.poll());
        if (chatType == ChatType.LOCAL) {
            event.setCancelled(true);
            return;
        }
        final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        final DataOutputStream outDataStream = new DataOutputStream(outByteStream);
        try {
            outDataStream.writeUTF(chatType.key());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        event.addExtraData(ExternalEssentialsReceiveListener.CHAT_TYPE_IDENTIFIER, outByteStream.toByteArray());
    }

}
