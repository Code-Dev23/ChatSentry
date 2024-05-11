package net.drugssmp.chatsentry.listeners;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import net.drugssmp.chatsentry.ChatSentry;
import net.drugssmp.chatsentry.utilities.cooldown.Cooldown;
import net.drugssmp.chatsentry.utilities.messages.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class ChatListeners implements Listener {
    private static final Cooldown cooldown = new Cooldown();
    private final ChatSentry main;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (main.getFilterManager().getProbableBots().contains(player.getUniqueId())) {
            CC.send(player, main.getConfig().getString("messages.anti_botspam"));
            event.setCancelled(true);
            return;
        }
        String message = event.getMessage();

        if (event.getMessage().contains("%"))
            message = message.replace("%", "%%");

        String format = PlaceholderAPI.setPlaceholders(player, main.getConfig().getString("settings.chat_format"));

        if (player.hasPermission("chatsentry.use.chat-colors"))
            message = CC.color(message);

        format = CC.color(format.replace("%message%", message));

        if (!player.hasPermission("chatsentry.bypass")) {
            if (main.getConfig().getBoolean("settings.banned_words") && main.getFilterManager().containsBadWord(message)) {
                CC.send(player, main.getConfig().getString("messages.banned_words"));
                event.setCancelled(true);
                return;
            }

            if (main.getConfig().getBoolean("settings.anti_caps") && main.getFilterManager().checkCaps(message, main.getConfig().getInt("settings.caps_percent"))) {
                CC.send(player, main.getConfig().getString("messages.anti_caps"));
                event.setCancelled(true);
                return;
            }

            if (main.getConfig().getBoolean("settings.anti_link") && main.getFilterManager().checkLink(message)) {
                CC.send(player, main.getConfig().getString("messages.anti_link"));
                event.setCancelled(true);
                return;
            }
            if (main.getConfig().getBoolean("settings.anti_spam")) {
                if (cooldown.isIn(player.getUniqueId())) {
                    CC.send(player, main.getConfig().getString("messages.anti_spam").replace("%time%", String.valueOf(cooldown.getTime(player.getUniqueId()))));
                    event.setCancelled(true);
                    return;
                }
                cooldown.put(player.getUniqueId(), main.getConfig().getInt("settings.cooldown_message"));
            }
        }
        event.setFormat(format);
    }
}