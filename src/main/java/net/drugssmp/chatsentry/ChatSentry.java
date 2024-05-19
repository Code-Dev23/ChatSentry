package net.drugssmp.chatsentry;

import com.google.common.collect.Maps;
import lombok.Getter;
import net.drugssmp.chatsentry.commands.ChatSentryCommand;
import net.drugssmp.chatsentry.commands.MessageCommand;
import net.drugssmp.chatsentry.commands.ReplyCommand;
import net.drugssmp.chatsentry.filter.FilterManager;
import net.drugssmp.chatsentry.listeners.AntiBotSpamListeners;
import net.drugssmp.chatsentry.listeners.ChatListeners;
import net.drugssmp.chatsentry.listeners.CommandListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Getter
public final class ChatSentry extends JavaPlugin {

    public static final Logger LOGGER = Bukkit.getLogger();
    private static ChatSentry instance;

    private FilterManager filterManager;
    private Map<UUID, UUID> pms;

    public static ChatSentry get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        filterManager = new FilterManager();
        pms = Maps.newHashMap();
        loadCommandsAndListeners();
    }

    private void loadCommandsAndListeners() {
        // COMMANDS

        getCommand("chatsentry").setExecutor(new ChatSentryCommand());
        getCommand("message").setExecutor(new MessageCommand(this));
        getCommand("reply").setExecutor(new ReplyCommand(this));

        // LISTENERS

        List.of(
                new ChatListeners(this),
                new AntiBotSpamListeners(this),
                new CommandListeners(this)
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }
}