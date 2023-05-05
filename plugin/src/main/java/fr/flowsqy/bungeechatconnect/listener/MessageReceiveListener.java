package fr.flowsqy.bungeechatconnect.listener;

import fr.flowsqy.bungeechatconnect.protocol.ChannelRegistry;
import fr.flowsqy.bungeechatconnect.protocol.task.AsyncMessageReceiver;
import fr.flowsqy.noqueuepluginmessage.api.event.DataReceiveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class MessageReceiveListener implements Listener {

    private final @NotNull Plugin plugin;

    public MessageReceiveListener(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onReceive(DataReceiveEvent event) {
        if (!ChannelRegistry.CHAT_CHANNEL.equals(event.getChannel())) {
            return;
        }
        AsyncMessageReceiver asyncMessageReceiver = new AsyncMessageReceiver();
        asyncMessageReceiver.receiveMessage(plugin, event.getData());
    }

}
