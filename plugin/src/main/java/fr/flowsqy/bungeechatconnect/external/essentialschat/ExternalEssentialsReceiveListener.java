package fr.flowsqy.bungeechatconnect.external.essentialschat;

import fr.flowsqy.bungeechatconnect.event.BungeePlayerChatEvent;
import net.ess3.api.IEssentials;
import net.essentialsx.api.v2.ChatType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ExternalEssentialsReceiveListener implements Listener {

    public final static String CHAT_TYPE_IDENTIFIER = "bungee_chat_connect:essentials_type";

    private final IEssentials essentialsPlugin;

    public ExternalEssentialsReceiveListener(@NotNull IEssentials essentialsPlugin) {
        this.essentialsPlugin = essentialsPlugin;
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onReceiveMessage(BungeePlayerChatEvent event) {
        final byte[] sentChatType = event.getExtraData().get(CHAT_TYPE_IDENTIFIER);
        final String chatType = sentChatType != null ? readChatType(sentChatType) : ChatType.UNKNOWN.key();
        final String permission = "essentials.chat.receive." + chatType;
        event.getRecipients().removeIf(player -> !essentialsPlugin.getUser(player).isAuthorized(permission));
    }

    @NotNull
    private String readChatType(byte[] sentChatType) {
        final DataInputStream inDataStream = new DataInputStream(new ByteArrayInputStream(sentChatType));
        try {
            return inDataStream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
