package com.wolfyxon.itemlimiter;

import org.bukkit.command.CommandSender;

public class ItemLimiterCommand {
    public ItemLimiter plugin;

    public ItemLimiterCommand(ItemLimiter plugin){
        this.plugin = plugin;
    }

    public ConfigMgr getConfig(){
        return plugin.configMgr;
    }

    public void sendMsg(String message, CommandSender sender){
        sender.sendMessage(Utils.format(message));
    }
}
