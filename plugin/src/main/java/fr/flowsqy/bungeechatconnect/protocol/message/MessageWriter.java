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
        outDataStream.writeUTF(sendMessageData.getName());
        outDataStream.writeUTF(sendMessageData.getDisplayName());
        outDataStream.writeUTF(sendMessageData.getFormat());
        outDataStream.writeUTF(sendMessageData.getMessage());
        final List<PrepareMessageEvent.ExtraData> extraDataList = sendMessageData.getExtraDataList();
        outDataStream.writeByte(extraDataList.size());
        for (PrepareMessageEvent.ExtraData extraData : extraDataList) {
            outDataStream.writeUTF(extraData.getPlugin().getName());
            outDataStream.writeShort(extraData.getData().length);
            outDataStream.write(extraData.getData());
        }
        return outByteStream.toByteArray();
    }

}
