package com.wolfyxon.itemlimiter;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permissible;

import java.util.Arrays;
import java.util.List;

public class ConfigMgr {
    FileConfiguration configFile;
    ItemLimiter plugin;
    static List<String> modes = Arrays.asList("clearmeta", "remove");

    public ConfigMgr(ItemLimiter plugin){
        this.plugin = plugin;
    }

    public void load(){
        configFile = plugin.getConfig();
    }

    public int getMaxDataSize(){
        return configFile.getInt("maxItemDataSize");
    }

    public int getMaxBookPages(){
        return configFile.getInt("maxBookPages");
    }

    public boolean getFeatureEnabled(String feature){
        return configFile.getBoolean("features."+feature);
    }

    public boolean itemScanningEnabled(){
        return getFeatureEnabled("itemScanning");
    }

    public boolean getItemScanningFeatureEnabled(String feature){
        return itemScanningEnabled() && getFeatureEnabled(feature);
    }

    public String getMode(){
        String defaultMode = "clearmeta";
        String mode = configFile.getString("mode").toLowerCase();
        if(mode.contains(mode)) return mode;
        return defaultMode;
    }

    public String getMessage(String localPath){
        return Utils.format( configFile.getString("messages."+localPath) );
    }

    public static boolean hasPermission(String permission, Permissible permissible){
        if(permissible instanceof ConsoleCommandSender) return true;
        if(permissible.isOp()) return true;
        String prefix = "itemlimiter.";
        return permissible.hasPermission(prefix+permission);
    }


}
