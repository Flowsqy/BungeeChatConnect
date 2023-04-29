package fr.flowsqy.bungeechatconnect.protocol.packet;

import fr.flowsqy.bungeechatconnect.protocol.NetworkRegistry;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketWriter {

    public byte @NotNull [] write(@NotNull String serverTo, byte @NotNull [] messageData) throws IOException {
        final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        final DataOutputStream outDataStream = new DataOutputStream(outByteStream);
        outDataStream.writeUTF("Forward");
        outDataStream.writeUTF(serverTo);
        outDataStream.writeUTF(NetworkRegistry.CHAT_CHANNEL);
        outDataStream.writeShort(messageData.length);
        outDataStream.write(messageData);
        return outByteStream.toByteArray();
    }

}
