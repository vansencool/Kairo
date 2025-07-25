package net.vansen.kairo.modules.impl.other;

import net.vansen.kairo.annotations.ClientChat;
import net.vansen.kairo.annotations.ClientCommand;
import net.vansen.kairo.events.ClientChatEvent;
import net.vansen.kairo.events.ClientCommandEvent;
import net.vansen.kairo.modules.Module;
import net.vansen.kairo.utils.ChatUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ClientChat
@ClientCommand
@SuppressWarnings("unused")
public class ChatRandomMessage implements ClientChatEvent, ClientCommandEvent, Module {

    private static final List<String> messages = List.of(
            "Did you know cats can make over 100 different sounds? :3",
            "This party is cuter than a baby sheep <3",
            "Plot twist: I was the ghost all along.",
            "Beep boop, I am your emotional support robot :3",
            "Guess what? I put pineapple on my pizza. Shocking, I know.",
            "Speedrunning life one respawn at a time :D",
            "I blinked and now I’m lost. Send help.",
            "If you're reading this, you're awesome. Yes, YOU! <3",
            "I bet you smiled just now. Caught ya! :3",
            "I came, I saw, I stole all the secrets.",
            "Fun fact: You can’t breathe while smiling. Try it :3",
            "Breaking news: Dungeon team has unmatched swag.",
            "If I vanish, assume I became a potato IRL.",
            "I don’t carry runs, I elevate them :0",
            "Warning: May spontaneously dance if loot is good.",
            "I trained with a villager for this moment.",
            "Shhh... I’m secretly a bat spying from the ceiling :0",
            "They told me I couldn’t do it. So I did it anyway <3",
            "Today I solo’d a boss using only a spoon. Mentally.",
            "Not lost, just exploring with extreme confusion :3",
            "Wait... this isn’t SkyBlock??",
            "I once sneezed and accidentally teleported. True story.",
            "Sometimes I stare at walls and think about good drops.",
            "I sleep in dungeons. Pretty comfy!",
            "Me? Carrying? Nooo, I just glow naturally :3",
            "Pro tip: If you get hit, just dodge better.",
            "If I disappear, assume I became one with the void <3",
            "I'm not AFK, I'm spiritually preparing.",
            "They said ‘go touch grass’. So I planted wheat.",
            "I believe in you. Yes, even after that last run <3",
            "Reminder: Falling is just flying... briefly.",
            "Life’s better with friends... and good drops.",
            "My pet rock said I’m cracked. I trust him.",
            "Sometimes I dream in SkyBlock.",
            "100% skill, 0% responsibility.",
            "I tried to be normal once. It was boring :<",
            "I’m not weird, I’m just built different... like necron different, get it? :3."
    );

    @Override
    public boolean clientChat(@NotNull String msg) {
        if (msg.trim().equalsIgnoreCase("!rm") || msg.trim().equalsIgnoreCase("!randommessage")) {
            ChatUtils.sendChat(messages.get(ThreadLocalRandom.current().nextInt(messages.size())));
            return false;
        }
        return true;
    }

    @Override
    public boolean clientCommand(@NotNull String message) {
        message = message.trim().toLowerCase();
        String command = "pc";
        if (message.contains("pc") || message.contains("pchat") || message.contains("p chat") || message.contains("party chat"))
            command = "pc";
        else if (message.contains("gc") || message.contains("gchat") || message.contains("g chat") || message.contains("guild chat"))
            command = "gc";
        if (message.contains("!ping")) {
            ChatUtils.sendChat("/" + command + " " + messages.get(ThreadLocalRandom.current().nextInt(messages.size())));
            return false;
        }
        return true;
    }
}