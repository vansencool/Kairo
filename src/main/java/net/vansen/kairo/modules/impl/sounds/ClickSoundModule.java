package net.vansen.kairo.modules.impl.sounds;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.vansen.kairo.annotations.ClientBlockUse;
import net.vansen.kairo.events.ClientBlockUseEvent;
import net.vansen.kairo.modules.Module;
import org.jetbrains.annotations.NotNull;

@ClientBlockUse
@SuppressWarnings("unused")
public class ClickSoundModule implements ClientBlockUseEvent, Module {

    @Override
    public void onUse(@NotNull BlockPos pos, @NotNull BlockState state) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        SoundEvent sound = state.getBlock() instanceof LeverBlock
                ? SoundEvents.BLOCK_LEVER_CLICK
                : (state.getBlock() instanceof ChestBlock ? SoundEvents.BLOCK_CHEST_OPEN : null);

        if (sound == null) return;
        client.world.playSound(
                client.player,
                pos,
                sound,
                SoundCategory.BLOCKS,
                1.5f,
                1.0f
        );
    }
}
