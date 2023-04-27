package fr.flowsqy.bungeechatconnect;

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
        outDataStream.writeUTF(ChannelRegistry.CHANNEL);
        outDataStream.writeShort(messageData.length);
        outDataStream.write(messageData);
        outDataStream.flush();
        return outByteStream.toByteArray();
    }

}
