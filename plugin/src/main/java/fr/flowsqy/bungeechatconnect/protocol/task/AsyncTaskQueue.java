package fr.flowsqy.bungeechatconnect.protocol.task;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AsyncTaskQueue {

    private final Plugin plugin;
    private final Queue<Runnable> queue;
    private boolean task;

    public AsyncTaskQueue(@NotNull Plugin plugin) {
        this.plugin = plugin;
        queue = new ConcurrentLinkedQueue<>();
    }

    public void subscribe(Runnable runnable) {
        synchronized (this) {
            queue.offer(runnable);
            if (task) {
                return;
            }
            Bukkit.getScheduler().runTaskAsynchronously(plugin, this::runAll);
            task = true;
        }
    }

    private void runAll() {
        synchronized (this) {
            Runnable runnable;
            while ((runnable = queue.poll()) != null) {
                runnable.run();
            }
            task = false;
        }
    }

}
