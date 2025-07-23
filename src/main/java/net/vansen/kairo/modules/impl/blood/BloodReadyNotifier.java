package net.vansen.kairo.modules.impl.blood;

import net.vansen.kairo.annotations.Chat;
import net.vansen.kairo.events.ChatEvent;
import net.vansen.kairo.modules.Module;
import org.jetbrains.annotations.NotNull;

@Chat
@SuppressWarnings("unused")
public class BloodReadyNotifier implements ChatEvent, Module {

    @Override
    public void chat(@NotNull String msg) {
        if (msg.trim().equals("[BOSS] The Watcher: That will be enough for now.")) {
            ChatEvent.sendChat("/pc Kairo -> Blood room is ready!");
        }
    }
}