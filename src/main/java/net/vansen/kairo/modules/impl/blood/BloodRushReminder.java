package net.vansen.kairo.modules.impl.blood;

import net.vansen.kairo.annotations.Chat;
import net.vansen.kairo.events.ChatEvent;
import net.vansen.kairo.modules.Module;
import net.vansen.kairo.utils.ChatUtils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Chat
@SuppressWarnings("unused")
public class BloodRushReminder implements ChatEvent, Module {
    private ScheduledFuture<?> task;
    private long startTime = -1;
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void chat(@NotNull String msg) {
        msg = msg.trim();

        if (msg.contains("Mort: Good luck.")) {
            cancelTask();
            startTime = System.currentTimeMillis();
            task = scheduler.schedule(() -> ChatUtils.sendChat("/pc Kairo -> Blood rush please (reminder: 2:30)"), 150, TimeUnit.SECONDS);
            return;
        }

        if (msg.contains("The BLOOD DOOR has been opened!") || msg.contains("[BOSS] The Watcher")) {
            if (startTime > 0) {
                long duration = (System.currentTimeMillis() - startTime) / 1000;
                if (duration < 10) {
                    ChatUtils.sendChat("/pc Kairo -> Blood rush completed in " + duration + "s <33333333333");
                } else if (duration < 20) {
                    ChatUtils.sendChat("/pc Kairo -> Blood rush completed in " + duration + "s :)");
                } else if (duration < 30) {
                    ChatUtils.sendChat("/pc Kairo -> Blood rush completed in " + duration + "s <3");
                } else if (duration < 60) {
                    ChatUtils.sendChat("/pc Kairo -> Blood rush completed in " + duration + "s :3");
                } else {
                    ChatUtils.sendChat("/pc Kairo -> Blood rush completed in " + duration + "s ><");
                }
            }
            startTime = -1;
            cancelTask();
            return;
        }

        if (msg.contains("has left the party.") ||
                msg.contains("You left the party.") ||
                msg.contains("Warping...")) {
            cancelTask();
        }
    }

    private void cancelTask() {
        if (task != null && !task.isDone()) {
            task.cancel(true);
        }
        task = null;
        startTime = -1;
    }
}
