package fr.flowsqy.bungeechatconnect;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PacketSender {

    public void send(Plugin plugin, Player sender, byte[] packetData) {
        sender.sendPluginMessage(plugin, "BungeeCord", packetData);
    }

}
