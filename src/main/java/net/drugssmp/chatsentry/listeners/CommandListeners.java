package net.drugssmp.chatsentry.listeners;

import lombok.RequiredArgsConstructor;
import net.drugssmp.chatsentry.ChatSentry;
import net.drugssmp.chatsentry.utilities.messages.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

@RequiredArgsConstructor
public class CommandListeners implements Listener {
    private final ChatSentry main;

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (main.getFilterManager().getProbableBots().contains(player.getUniqueId())) {
            CC.send(player, main.getConfig().getString("messages.anti_botspam"));
            event.setCancelled(true);
        }
    }
}
