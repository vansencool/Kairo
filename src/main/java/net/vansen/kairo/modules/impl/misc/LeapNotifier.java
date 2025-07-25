package net.vansen.kairo.modules.impl.misc;

import net.vansen.kairo.annotations.Chat;
import net.vansen.kairo.events.ChatEvent;
import net.vansen.kairo.modules.Module;
import net.vansen.kairo.utils.ChatUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Chat
@SuppressWarnings("unused")
public class LeapNotifier implements ChatEvent, Module {

    private static long lastSent = System.currentTimeMillis();

    @Override
    public void chat(@NotNull String msg) {
        Matcher m = Pattern.compile("^You have teleported to (.+)")
                .matcher(msg);
        if (m.find()) {
            if (System.currentTimeMillis() - lastSent < 1000) return;
            ChatUtils.sendChat("/pc Kairo -> Leaped to " + m.group(1).replace("!", ""));
            lastSent = System.currentTimeMillis();
        }
    }
}