package net.vansen.kairo.events;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface ClientBlockUseEvent {
    void onUse(@NotNull BlockPos pos, @NotNull BlockState state);

    List<ClientBlockUseEvent> listeners = new ArrayList<>();

    static void register(@NotNull ClientBlockUseEvent listener) {
        listeners.add(listener);
    }

    static void dispatch(@NotNull BlockPos pos, @NotNull BlockState state) {
        for (ClientBlockUseEvent listener : listeners) {
            listener.onUse(pos, state);
        }
    }
}