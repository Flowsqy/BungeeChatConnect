package fr.flowsqy.bungeechatconnect.protocol.message;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MessageReader {

    @NotNull
    public ReceivedMessage read(byte @NotNull [] messageData) throws IOException {
        final DataInputStream inDataStream = new DataInputStream(new ByteArrayInputStream(messageData));
        final String name = inDataStream.readUTF();
        final String displayName = inDataStream.readUTF();
        final String format = inDataStream.readUTF();
        final String message = inDataStream.readUTF();
        final byte extraDataNumber = inDataStream.readByte();
        final Map<String, byte[]> extraData = new HashMap<>(extraDataNumber);
        for (int i = 0; i < extraDataNumber; i++) {
            final String pluginName = inDataStream.readUTF();
            final byte[] pluginExtraData = new byte[inDataStream.readShort()];
            inDataStream.readFully(pluginExtraData);
            extraData.put(pluginName, pluginExtraData);
        }
        return new ReceivedMessage(name, displayName, format, message, extraData);
    }

}
