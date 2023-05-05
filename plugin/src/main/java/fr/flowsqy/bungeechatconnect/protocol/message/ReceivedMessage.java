package fr.flowsqy.bungeechatconnect.protocol.message;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public record ReceivedMessage(@NotNull String name, @NotNull String displayName, @NotNull String format,
                              @NotNull String message, @NotNull Map<String, byte[]> extraData) {

}
