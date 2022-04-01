package fr.robotv2.cymerlinetools.util;

import org.bukkit.command.CommandSender;

public class StringUtil {

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ColorAPI.colorize(message));
    }
}
