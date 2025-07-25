package net.vansen.kairo.modules.impl.other;

import net.minecraft.client.MinecraftClient;
import net.vansen.kairo.annotations.ClientChat;
import net.vansen.kairo.annotations.ClientCommand;
import net.vansen.kairo.events.ClientChatEvent;
import net.vansen.kairo.events.ClientCommandEvent;
import net.vansen.kairo.modules.Module;
import net.vansen.kairo.utils.ChatUtils;
import org.jetbrains.annotations.NotNull;

@ClientChat
@ClientCommand
@SuppressWarnings("unused")
public class ChatFps implements ClientChatEvent, ClientCommandEvent, Module {

    @Override
    public boolean clientChat(@NotNull String msg) {
        if (msg.trim().equalsIgnoreCase("!fps")) {
            ChatUtils.sendChat("FPS -> " + MinecraftClient.getInstance().getCurrentFps());
            return false;
        }
        return true;
    }

    @Override
    public boolean clientCommand(@NotNull String message) {
        message = message.trim().toLowerCase();
        String command = "pc";
        if (message.contains("pc")) command = "pc";
        else if (message.contains("gc")) command = "gc";
        if (message.contains("!fps")) {
            ChatUtils.sendChat("/" + command + " FPS -> " + MinecraftClient.getInstance().getCurrentFps());
            return false;
        }
        return true;
    }
}