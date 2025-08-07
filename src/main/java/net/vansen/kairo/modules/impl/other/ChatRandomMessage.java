package net.vansen.kairo.modules.impl.other;

import net.vansen.kairo.annotations.ClientChat;
import net.vansen.kairo.annotations.ClientCommand;
import net.vansen.kairo.events.ClientChatEvent;
import net.vansen.kairo.events.ClientCommandEvent;
import net.vansen.kairo.modules.Module;
import net.vansen.kairo.utils.ChatUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@ClientChat
@ClientCommand
@SuppressWarnings("unused")
public class ChatRandomMessage implements ClientChatEvent, ClientCommandEvent, Module {

    private static final List<String> random = List.of(
            "Did you know cats can make over 100 different sounds? :3",
            "This party is cuter than a baby sheep <3",
            "Plot twist: I was the ghost all along.",
            "Beep boop, I am your emotional support robot :3",
            "Guess what? I put pineapple on my pizza. Shocking, I know.",
            "Speedrunning life one respawn at a time :D",
            "I blinked and now I’m lost. Send help.",
            "If you're reading this, you're awesome. Yes, YOU! <3",
            "Fun fact: You can’t breathe while smiling. Try it :3",
            "Breaking news: Dungeon team has unmatched swag.",
            "If I vanish, assume I became a potato IRL.",
            "I don’t carry runs, I elevate them :0",
            "Warning: May spontaneously dance if loot is good.",
            "I trained with a villager for this moment.",
            "Shhh... I’m secretly a bat spying from the ceiling :0",
            "They told me I couldn’t do it. So I did it anyway <3",
            "Today I solo’d a boss using only a spoon. Mentally.",
            "Not lost, just playing with extreme confusion :3",
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

    private static final List<String> wholesome = List.of(
            "Hey you. You're doing amazing. Just thought you should know <3",
            "Smile check! You look better when you do :3",
            "You're not just good at the game, you're good in general.",
            "This run’s already better because you’re here :3",
            "I hope something unexpectedly nice happens to you today!",
            "Fun fact: There’s no one else like you in the whole server <3",
            "You're someone's favorite teammate. Maybe mine :0",
            "The stars look brighter when you're online :3",
            "If happiness was loot, you'd be a legendary drop.",
            "Someone in this party has great vibes. Spoiler: it’s you.",
            "You're basically the MVP of life right now.",
            "If you feel tired, it’s okay to rest. You still rock.",
            "You're more clutch than a 1HP finish <3",
            "Don't forget: you're allowed to be proud of yourself!",
            "You make this party 1000% cooler just by being in it.",
            "You survived another day. That’s huge. I’m proud of you.",
            "You're like a crit hit — powerful and unexpected :3",
            "Reminder: You deserve good things. Always.",
            "You're not ‘just playing’. You're making memories <3",
            "This is your daily compliment. You earned it.",
            "I’m cheering for you silently. Like a ghost teammate but friendlier :3",
            "Hope today gives you something to smile about!",
            "Just in case no one said it: You're awesome. Period.",
            "Even if the loot is bad, you're still a win in my book <3",
            "You're doing way better than you think."
    );

    private static final Set<String> RANDOM_COMMANDS = Set.of("!rm", "!randommessage");
    private static final Set<String> WHOLESOME_COMMANDS = Set.of("!wm", "!wsm", "!wholesome", "!wholesome message", "!wholesomemessage");

    @Override
    public boolean clientChat(@NotNull String msg) {
        if (RANDOM_COMMANDS.contains(msg.trim().toLowerCase())) {
            ChatUtils.sendChat(random.get(ThreadLocalRandom.current().nextInt(random.size())));
            return false;
        }

        if (WHOLESOME_COMMANDS.contains(msg.trim().toLowerCase())) {
            ChatUtils.sendChat(wholesome.get(ThreadLocalRandom.current().nextInt(wholesome.size())));
            return false;
        }
        return true;
    }

    @Override
    public boolean clientCommand(@NotNull String message) {
        message = message.trim().toLowerCase();
        String command = null;
        if (message.contains("pc") || message.contains("pchat") || message.contains("p chat") || message.contains("party chat"))
            command = "pc";
        else if (message.contains("gc") || message.contains("gchat") || message.contains("g chat") || message.contains("guild chat"))
            command = "gc";
        if (command == null) return true;

        //

        if (RANDOM_COMMANDS.stream().anyMatch(message::contains)) {
            ChatUtils.sendChat("/" + command + " " + random.get(ThreadLocalRandom.current().nextInt(random.size())));
            return false;
        }
        if (WHOLESOME_COMMANDS.stream().anyMatch(message::contains)) {
            ChatUtils.sendChat("/" + command + " " + wholesome.get(ThreadLocalRandom.current().nextInt(wholesome.size())));
            return false;
        }
        return true;
    }
}