package net.vansen.kairo.modules.impl.gui;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import net.vansen.kairo.annotations.CommandRegister;
import net.vansen.kairo.events.CommandRegisterEvent;
import net.vansen.kairo.modules.Module;
import org.jetbrains.annotations.NotNull;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

// TODO
@CommandRegister
@SuppressWarnings("unused")
public class KairoCommand implements CommandRegisterEvent, Module {

    @Override
    public void register(@NotNull CommandDispatcher<FabricClientCommandSource> dispatcher, @NotNull CommandRegistryAccess registryAccess) {
        dispatcher.register(literal("kairo")
                .executes(context -> {
                    context.getSource().sendFeedback(Text.literal("Does nothing for now... but will open the Kairo GUI in the future!"));
                    return 1;
                }));
    }
}
