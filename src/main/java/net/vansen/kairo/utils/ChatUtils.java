package net.vansen.kairo.utils;

import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public class ChatUtils {

    public static void sendChat(@NotNull String msg) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.networkHandler.sendChatMessage(msg);
    }
}