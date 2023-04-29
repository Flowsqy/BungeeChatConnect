package fr.flowsqy.bungeechatconnect.protocol.queue;

import fr.flowsqy.bungeechatconnect.protocol.message.SendMessageData;
import fr.flowsqy.bungeechatconnect.protocol.process.NetworkMessageSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {

    private final Plugin plugin;
    private final QueueTimer timer;
    private final PlayerCountQuery playerCountChecker;
    private final String server;
    private final Queue<SendMessageData> queue;

    public MessageQueue(@NotNull Plugin plugin, int expirationTime, @NotNull String server) {
        this.plugin = plugin;
        this.timer = new QueueTimer(expirationTime);
        this.playerCountChecker = new PlayerCountQuery(plugin, server, this::updateCallback);
        this.server = server;
        queue = new ConcurrentLinkedQueue<>();
    }

    public void register() {
        playerCountChecker.register();
    }

    public void unregister() {
        playerCountChecker.unregister();
    }

    public void subscribe(@NotNull Player player, @NotNull SendMessageData sendMessageData) {
        queue.offer(sendMessageData);
        checkForSend(player);
    }

    private void updateCallback(@NotNull Player player) {
        timer.updateTime();
        checkForSend(player);
    }

    private void checkForSend(@NotNull Player player) {
        if (timer.needUpdate()) {
            playerCountChecker.query(player);
            return;
        }
        processCurrent(player);
    }

    private void processCurrent(@NotNull Player player) {
        if (playerCountChecker.havePlayer()) {
            sendAll(player);
        }
        queue.clear();
    }

    private void sendAll(@NotNull Player player) {
        final NetworkMessageSender networkMessageSender = new NetworkMessageSender();
        SendMessageData sendMessageData;
        while ((sendMessageData = queue.poll()) != null) {
            networkMessageSender.sendMessage(plugin, player, server, sendMessageData);
        }
    }

    @NotNull
    public String getServer() {
        return server;
    }
}
