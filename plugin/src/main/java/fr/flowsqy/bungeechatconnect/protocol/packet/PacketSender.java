package fr.flowsqy.bungeechatconnect.protocol.packet;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PacketSender {

    public void send(@NotNull Plugin plugin, @NotNull Player sender, byte @NotNull [] packetData) {
        sender.sendPluginMessage(plugin, "BungeeCord", packetData);
    }

}
