package net.vansen.kairo.events;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface CommandRegisterEvent {
    void register(@NotNull CommandDispatcher<FabricClientCommandSource> dispatcher, @NotNull CommandRegistryAccess registryAccess);

    List<CommandRegisterEvent> listeners = new ArrayList<>();

    static void register(@NotNull CommandRegisterEvent listener) {
        listeners.add(listener);
    }

    static void dispatch(@NotNull CommandDispatcher<FabricClientCommandSource> dispatcher, @NotNull CommandRegistryAccess registryAccess) {
        for (CommandRegisterEvent listener : listeners) {
            listener.register(dispatcher, registryAccess);
        }
    }
}