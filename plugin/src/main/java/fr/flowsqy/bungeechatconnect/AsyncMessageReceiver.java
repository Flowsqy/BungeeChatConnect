package fr.flowsqy.bungeechatconnect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class AsyncMessageReceiver {

    public void receiveMessage(@NotNull Plugin plugin, byte @NotNull [] packetData) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new ReceiveMessageRunnable(true, packetData));
    }

}
