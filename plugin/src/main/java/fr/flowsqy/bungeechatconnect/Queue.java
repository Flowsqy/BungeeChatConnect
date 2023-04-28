package fr.flowsqy.bungeechatconnect;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Queue {

    private final int expirationTime = 1000;
    private final String server = "";
    private final java.util.Queue<SendMessageData> queue = new ConcurrentLinkedQueue<>();
    private final long lastCheck = 0;

    public void subscribe(@NotNull Plugin plugin, @NotNull Player player, @NotNull SendMessageData sendMessageData) {
        queue.offer(sendMessageData);
        checkForSend(plugin, player);
    }

    private void checkForSend(@NotNull Plugin plugin, @NotNull Player player) {
        if (System.currentTimeMillis() - lastCheck > expirationTime) {
            queryPlayerCount(plugin, player);
            return;
        }
        processCurrent(plugin, player);
    }

    private void queryPlayerCount(@NotNull Plugin plugin, @NotNull Player player) {

    }

    private void processCurrent(@NotNull Plugin plugin, @NotNull Player player) {
        final NetworkMessageSender networkMessageSender = new NetworkMessageSender();
        // TODO Process every element
        networkMessageSender.sendMessage(plugin, player, server, queue.poll());
    }

    @NotNull
    public String getServer() {
        return server;
    }
}
