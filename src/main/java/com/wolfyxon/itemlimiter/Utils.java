package com.wolfyxon.itemlimiter;

import org.bukkit.ChatColor;

public class Utils {
    public static String format(String text){
        if(text == null) return "";
        return ChatColor.translateAlternateColorCodes('&',text);
    }
}
