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


}
