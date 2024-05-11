package net.drugssmp.chatsentry.listeners;

import lombok.RequiredArgsConstructor;
import net.drugssmp.chatsentry.ChatSentry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class AntiBotSpamListeners implements Listener {
    private final ChatSentry main;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        main.getFilterManager().getProbableBots().add(player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        main.getFilterManager().getProbableBots().remove(player.getUniqueId());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        main.getFilterManager().getProbableBots().remove(player.getUniqueId());
    }
}
