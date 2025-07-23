package net.vansen.kairo.events;

import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface ChatEvent {
    void chat(@NotNull String message);

    List<ChatEvent> listeners = new ArrayList<>();

    static void register(@NotNull ChatEvent listener) {
        listeners.add(listener);
    }

    static void dispatch(@NotNull String message) {
        for (ChatEvent listener : listeners) {
            listener.chat(message);
        }
    }

    static void sendChat(@NotNull String msg) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.networkHandler.sendChatMessage(msg);
    }
}