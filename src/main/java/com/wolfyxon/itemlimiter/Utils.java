package com.wolfyxon.itemlimiter;

import org.bukkit.ChatColor;

import java.util.Map;

public class Utils {
    public static String format(String text){
        if(text == null) return "";
        return ChatColor.translateAlternateColorCodes('&',text);
    }
    public static String replaceMultiple(String str, Map<String,String> replacements){
        for(Map.Entry<String,String> e : replacements.entrySet()){
            str.replace(e.getKey(),e.getValue());
        }
        return str;
    }
}
