package net.vansen.kairo.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.vansen.kairo.chat.ChatFilters;
import net.vansen.kairo.events.ChatEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMixin {
    @Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
    private void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
        String message = packet.content().getString().replaceAll("§[0-9a-fk-or]", "");
        ChatEvent.dispatch(message);

        if (ChatFilters.shouldBlock(message)) {
            ci.cancel();
        }
    }
}