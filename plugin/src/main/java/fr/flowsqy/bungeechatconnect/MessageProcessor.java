package fr.flowsqy.bungeechatconnect;

import org.jetbrains.annotations.NotNull;

public class MessageProcessor {

    private final static String PLACEHOLDER = "{SERVER}";

    @NotNull
    public String process(@NotNull String format, @NotNull String serverName) {
        return format.replace(PLACEHOLDER, serverName);
    }

}
