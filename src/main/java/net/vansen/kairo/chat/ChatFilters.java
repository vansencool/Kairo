package net.vansen.kairo.chat;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Credits: <a href="https://github.com/kiwidotzip/MeowAddons/blob/main/chatcleanerlist.txt">Meow Addons</a>
 */
public class ChatFilters {
    public static final List<Pattern> patterns = new ArrayList<>();

    static {
        String block = """
                .+ found a Wither Essence! Everyone gains an extra essence!
                The .+ Trap hit you for .+ damage!
                Healer Milestone.+
                Archer Milestone.+
                Mage Milestone.+
                Tank Milestone.+
                Berserk Milestone.+
                .+has obtained.+
                Your .+ stats are doubled because you are the only player using this class!
                .+ Milestone .+: .+
                Your CLASS stats are doubled because you are the only player using this class!
                A Crypt Wither Skull exploded, hitting you for .+ damage.
                A Blessing of .+ was picked up!
                .*Also granted you.+
                The Lost Adventurer used Dragon's Breath on you!
                A Blessing of .+
                Your Berserk ULTIMATE Ragnarok is now available!
                Granted you.+
                This item's ability is temporarily disabled!
                Throwing Axe is now available!
                Used Throwing Axe!
                PUZZLE SOLVED!.+
                DUNGEON BUFF! .+
                Don't move diagonally! Bad!
                Oops! You stepped on the wrong block!
                Used Ragnarok!
                Your bone plating reduced the damage you took by .+!
                """;

        for (String line : block.split("\n")) {
            patterns.add(Pattern.compile(line));
        }
    }

    public static boolean shouldBlock(@NotNull String msg) {
        for (Pattern p : patterns) {
            if (p.matcher(msg).matches()) {
                return true;
            }
        }
        return false;
    }
}

