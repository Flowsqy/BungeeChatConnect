package fr.flowsqy.bungeechatconnect.external.essentialschat;

import com.earth2me.essentials.chat.EssentialsChat;
import net.ess3.api.IEssentials;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ExternalEssentialsChatLoader {

    public void load(@NotNull Plugin plugin) {
        final Plugin essentialChatPlugin = Bukkit.getPluginManager().getPlugin("EssentialsChat");
        if (essentialChatPlugin == null || !essentialChatPlugin.isEnabled()) {
            return;
        }
        if (!(essentialChatPlugin instanceof EssentialsChat)) {
            return;
        }
        enable(plugin);
    }

    private void enable(@NotNull Plugin plugin) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        final IEssentials essentialsPlugin = (IEssentials) pluginManager.getPlugin("Essentials");
        Objects.requireNonNull(essentialsPlugin);
        final ExternalEssentialsChatListener messageListener = new ExternalEssentialsChatListener();
        final ExternalEssentialsReceiveListener receiveListener = new ExternalEssentialsReceiveListener(essentialsPlugin);
        pluginManager.registerEvents(messageListener, plugin);
        pluginManager.registerEvents(receiveListener, plugin);
    }

}
