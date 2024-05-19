package net.drugssmp.chatsentry.commands;

import lombok.RequiredArgsConstructor;
import net.drugssmp.chatsentry.ChatSentry;
import net.drugssmp.chatsentry.utilities.messages.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MessageCommand implements CommandExecutor {
    private final ChatSentry plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player))
            return false;

        if(args.length <= 1) {
            CC.send(player, plugin.getConfig().getString("messages.usage_message"));
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            CC.send(player, plugin.getConfig().getString("messages.player_not_found"));
            return false;
        }
        if(player == target) {
            CC.send(player, plugin.getConfig().getString("messages.cant_send_yourself"));
            return false;
        }

        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < args.length; i++)
            builder.append(args[i]).append(" ");

        CC.send(player, plugin.getConfig().getString("messages.format_pm_sender").replace("%receiver%", target.getName()).replace("%message%", builder));
        CC.send(target, plugin.getConfig().getString("messages.format_pm_receiver").replace("%sender%", player.getName()).replace("%message%", builder));

        plugin.getPms().put(player.getUniqueId(), target.getUniqueId());
        plugin.getPms().put(target.getUniqueId(), player.getUniqueId());
        return true;
    }
}
