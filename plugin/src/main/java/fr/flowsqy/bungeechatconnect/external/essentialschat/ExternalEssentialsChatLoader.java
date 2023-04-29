package fr.flowsqy.bungeechatconnect.external.essentialschat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ExternalEssentialsChatLoader {

    public void load(@NotNull Plugin plugin) {
        final Plugin essentialChatPlugin = Bukkit.getPluginManager().getPlugin("EssentialsChat");
        if (essentialChatPlugin == null) {
            return;
        }
        enable(plugin);
    }

    private void enable(@NotNull Plugin plugin) {
        final ExternalEssentialsChatListener messageListener = new ExternalEssentialsChatListener();
        messageListener.enable();
        Bukkit.getPluginManager().registerEvents(messageListener, plugin);
    }

}
