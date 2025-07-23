package net.vansen.kairo.modules.impl.misc;

import net.vansen.kairo.annotations.Chat;
import net.vansen.kairo.events.ChatEvent;
import net.vansen.kairo.modules.Module;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Chat
@SuppressWarnings("unused")
public class LeapNotifier implements ChatEvent, Module {

    @Override
    public void chat(@NotNull String msg) {
        Matcher m = Pattern.compile("^You have teleported to (.+)")
                .matcher(msg);
        if (m.find()) ChatEvent.sendChat("/pc Kairo -> Leaped to " + m.group(1).replace("!", ""));
    }
}