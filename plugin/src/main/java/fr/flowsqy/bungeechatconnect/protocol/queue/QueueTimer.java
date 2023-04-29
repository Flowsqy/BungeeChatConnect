package fr.flowsqy.bungeechatconnect.protocol.queue;

public class QueueTimer {

    private final int expirationTime;
    private volatile long lastCheck;

    public QueueTimer(int expirationTime) {
        this.expirationTime = expirationTime;
        lastCheck = 0;
    }

    public boolean needUpdate() {
        return System.currentTimeMillis() - lastCheck > expirationTime;
    }

    public void updateTime() {
        this.lastCheck = System.currentTimeMillis();
    }

}
