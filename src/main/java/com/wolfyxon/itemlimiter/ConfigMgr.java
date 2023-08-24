package com.wolfyxon.itemlimiter;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigMgr {
    FileConfiguration configFile;
    ItemLimiter plugin;

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

    public String getMessage(String localPath){
        return Utils.format( configFile.getString("messages."+localPath) );
    }


}
