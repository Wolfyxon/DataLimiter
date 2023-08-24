package com.wolfyxon.itemlimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class ItemLimiterCommand implements CommandExecutor, TabCompleter {
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
