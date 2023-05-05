package fr.flowsqy.bungeechatconnect.protocol.message;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class MessageWriter {

    public byte @NotNull [] write(@NotNull SendMessageData sendMessageData) throws IOException {
        final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        final DataOutputStream outDataStream = new DataOutputStream(outByteStream);
        outDataStream.writeUTF(sendMessageData.name());
        outDataStream.writeUTF(sendMessageData.displayName());
        outDataStream.writeUTF(sendMessageData.format());
        outDataStream.writeUTF(sendMessageData.message());
        final List<PrepareMessageEvent.ExtraData> extraDataList = sendMessageData.extraDataList();
        outDataStream.writeByte(extraDataList.size());
        for (PrepareMessageEvent.ExtraData extraData : extraDataList) {
            outDataStream.writeUTF(extraData.identifier());
            outDataStream.writeShort(extraData.data().length);
            outDataStream.write(extraData.data());
        }
        return outByteStream.toByteArray();
    }

}
