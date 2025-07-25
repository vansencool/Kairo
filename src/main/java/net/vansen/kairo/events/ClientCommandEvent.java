package net.vansen.kairo.events;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface ClientCommandEvent {

    /**
     * Called when a client command is executed.
     *
     * @param message The command message to be processed.
     * @return true if the command should be processed, false to cancel processing.
     */
    boolean clientCommand(@NotNull String message);

    List<ClientCommandEvent> listeners = new ArrayList<>();

    static void register(@NotNull ClientCommandEvent listener) {
        listeners.add(listener);
    }

    static boolean dispatch(@NotNull String message) {
        boolean send = true;
        for (ClientCommandEvent listener : listeners) {
            if (!listener.clientCommand(message)) {
                send = false;
            }
        }
        return send;
    }
}