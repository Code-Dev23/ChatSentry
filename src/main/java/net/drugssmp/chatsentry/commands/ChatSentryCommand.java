package net.drugssmp.chatsentry.commands;

import net.drugssmp.chatsentry.ChatSentry;
import net.drugssmp.chatsentry.utilities.messages.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChatSentryCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            CC.send((Player) sender, "&aPlugin created by Scopped_ <3");
            return true;
        }
        if (args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            ChatSentry.get().reloadConfig();
            sender.sendMessage(CC.color("&aPlugin reloaded with successful!"));
            return true;
        }
        CC.send((Player) sender, "&aPlugin created by Scopped_ <3");
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player && !player.isOnline())
            return List.of("");
        return List.of("reload");
    }
}
