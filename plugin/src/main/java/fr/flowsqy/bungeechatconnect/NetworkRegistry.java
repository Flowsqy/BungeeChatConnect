package fr.flowsqy.bungeechatconnect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class NetworkRegistry {

    public final static String CHAT_CHANNEL = "bungee_chat_connect:chat";

    public void registerOut(@NotNull Plugin plugin) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
    }

    public void unregisterOut(@NotNull Plugin plugin) {
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, "BungeeCord");
    }

}
