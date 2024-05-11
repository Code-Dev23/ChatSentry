package net.drugssmp.chatsentry.utilities.messages;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CC {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String color(Player player, String text) {
        return PlaceholderAPI.setPlaceholders(player, color(text));
    }

    public static void send(Player player, String text) {
        player.sendMessage(color(player, text));
    }
}
