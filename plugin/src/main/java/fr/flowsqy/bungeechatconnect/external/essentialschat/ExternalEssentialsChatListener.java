package fr.flowsqy.bungeechatconnect.external.essentialschat;

import fr.flowsqy.bungeechatconnect.BungeeChatConnectPlugin;
import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import net.essentialsx.api.v2.ChatType;
import net.essentialsx.api.v2.events.chat.GlobalChatEvent;
import net.essentialsx.api.v2.events.chat.LocalChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ExternalEssentialsChatListener implements Listener {

    private final Map<UUID, ChatType> messagesTypes;

    public ExternalEssentialsChatListener() {
        messagesTypes = new ConcurrentHashMap<>();
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    private void onChat(AsyncPlayerChatEvent event) {
        // Register the message by default
        subscribe(event.getPlayer().getUniqueId(), ChatType.UNKNOWN);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    private void onChat(LocalChatEvent event) {
        subscribe(event.getPlayer().getUniqueId(), event.getChatType());
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    private void onChat(GlobalChatEvent event) {
        subscribe(event.getPlayer().getUniqueId(), event.getChatType());
    }

    private void subscribe(UUID senderId, ChatType chatType) {
        messagesTypes.put(senderId, chatType);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    private void onPrepare(PrepareMessageEvent event) {
        final ChatType chatType = messagesTypes.remove(event.getPlayer().getUniqueId());
        if (chatType == null) {
            final String warning = "Somehow, the EssentialsChat support didn't manage to get the chat type for the message of '" + event.getPlayer().getName() + "'. The message will be silenced. Please report the bug to the plugin author and specify your server software";
            JavaPlugin.getPlugin(BungeeChatConnectPlugin.class).getLogger().warning(warning);
            return;
        }
        if (event.isCancelled()) {
            return;
        }
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
