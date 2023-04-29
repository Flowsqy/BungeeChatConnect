package fr.flowsqy.bungeechatconnect.external;

import fr.flowsqy.bungeechatconnect.external.essentialschat.ExternalEssentialsChatLoader;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ExternalManager {

    public void load(@NotNull Plugin plugin) {
        final ExternalEssentialsChatLoader essentialsChatLoader = new ExternalEssentialsChatLoader();
        essentialsChatLoader.load(plugin);
    }

}
