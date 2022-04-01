package fr.robotv2.cymerlinetools.util;

import org.bukkit.ChatColor;

public class ColorAPI {

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String unColorize(String message) {
        return ChatColor.stripColor(message);
    }
}
