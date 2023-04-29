package fr.flowsqy.bungeechatconnect;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PacketReader {

    public Result read(byte @NotNull [] packetData) throws IOException {
        final DataInputStream inDataStream = new DataInputStream(new ByteArrayInputStream(packetData));
        if (!inDataStream.readUTF().equals(NetworkRegistry.CHAT_CHANNEL)) {
            return new Result(false, new byte[0]);
        }
        final byte[] messageData = new byte[inDataStream.readShort()];
        inDataStream.readFully(messageData);
        return new Result(true, messageData);
    }

    public final static class Result {
        private final boolean valid;
        private final byte[] messageData;

        public Result(boolean valid, byte @NotNull [] messageData) {
            this.valid = valid;
            this.messageData = messageData;
        }

        public boolean isValid() {
            return valid;
        }

        public byte[] getMessageData() {
            return messageData;
        }
    }

}
