package net.drugssmp.chatsentry.filter;

import lombok.Getter;
import net.drugssmp.chatsentry.ChatSentry;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class FilterManager {
    private final List<String> words;
    private final Set<UUID> probableBots = new HashSet<>();

    public FilterManager() {
        words = new ArrayList<>();
        words.addAll(ChatSentry.get().getConfig().getStringList("banned_words"));
    }

    public boolean containsBadWord(String message) {
        final String finalWord = normalizeWord(message);
        return words.stream().anyMatch(finalWord::contains);
    }

    private String normalizeWord(String message) {
        message = message.toLowerCase().trim()
                .replaceAll("3", "e")
                .replaceAll("4", "a")
                .replaceAll("5", "s")
                .replaceAll("1", "i")
                .replaceAll("0", "o");
        return message;
    }

    public boolean checkLink(String message) {
        String regex = "(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ;,./?%&=]*)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        return matcher.find();
    }

    public boolean checkCaps(String message, int percent) {
        long uppercaseCount = message.chars().filter(Character::isUpperCase).count();
        double capsPercentage = (double) uppercaseCount / message.length() * 100;
        return capsPercentage > percent;
    }
}