package fr.flowsqy.bungeechatconnect.protocol.message;

import fr.flowsqy.bungeechatconnect.event.PrepareMessageEvent;

import java.util.List;

public class SendMessageData {

    private final String format;
    private final String message;
    private final List<PrepareMessageEvent.ExtraData> extraDataList;

    public SendMessageData(String format, String message, List<PrepareMessageEvent.ExtraData> extraDataList) {
        this.format = format;
        this.message = message;
        this.extraDataList = extraDataList;
    }

    public static SendMessageData snapshot(PrepareMessageEvent event) {
        return new SendMessageData(event.getFormat(), event.getMessage(), event.getExtraDataList());
    }

    public String getFormat() {
        return format;
    }

    public String getMessage() {
        return message;
    }

    public List<PrepareMessageEvent.ExtraData> getExtraDataList() {
        return extraDataList;
    }

}
