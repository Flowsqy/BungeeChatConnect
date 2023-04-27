package fr.flowsqy.bungeechatconnect;

import org.bukkit.plugin.java.JavaPlugin;

public class BungeeChatConnectPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final ChannelRegistry channelRegistry = new ChannelRegistry();
        channelRegistry.registerChannel(this);
    }

    @Override
    public void onDisable() {
        final ChannelRegistry channelRegistry = new ChannelRegistry();
        channelRegistry.unregisterChannel(this);
    }
}
