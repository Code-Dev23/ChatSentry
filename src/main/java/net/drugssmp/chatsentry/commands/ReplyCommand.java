package net.drugssmp.chatsentry.commands;

import lombok.RequiredArgsConstructor;
import net.drugssmp.chatsentry.ChatSentry;
import net.drugssmp.chatsentry.utilities.messages.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@RequiredArgsConstructor
public class ReplyCommand implements CommandExecutor {
    private final ChatSentry plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player))
            return false;

        if(args.length == 0) {
            CC.send(player, plugin.getConfig().getString("messages.usage_reply"));
            return false;
        }
        if(!plugin.getPms().containsKey(player.getUniqueId())) {
            CC.send(player, plugin.getConfig().getString("messages.dont_have_conversations"));
            return false;
        }

        UUID uuid = plugin.getPms().get(player.getUniqueId());
        Player target = Bukkit.getPlayer(uuid);
        if(target == null) {
            CC.send(player, plugin.getConfig().getString("messages.dont_have_conversations"));
            return false;
        }

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < args.length; i++)
            builder.append(args[i]).append(" ");

        CC.send(player, plugin.getConfig().getString("messages.format_pm_sender").replace("%receiver%", target.getName()).replace("%message%", builder));
        CC.send(target, plugin.getConfig().getString("messages.format_pm_receiver").replace("%sender%", player.getName()).replace("%message%", builder));
        return true;
    }
}
