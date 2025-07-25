package net.vansen.kairo.events;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface ClientChatEvent {

    /**
     * Called when a chat message is sent by the client.
     *
     * @param message The chat message to be sent.
     * @return true if the message should be sent, false to cancel sending.
     */
    boolean clientChat(@NotNull String message);

    List<ClientChatEvent> listeners = new ArrayList<>();

    static void register(@NotNull ClientChatEvent listener) {
        listeners.add(listener);
    }

    static boolean dispatch(@NotNull String message) {
        boolean send = true;
        for (ClientChatEvent listener : listeners) {
            if (!listener.clientChat(message)) {
                send = false;
            }
        }
        return send;
    }
}