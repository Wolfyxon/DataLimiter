package com.wolfyxon.itemlimiter.commands;

import com.wolfyxon.itemlimiter.ConfigMgr;
import com.wolfyxon.itemlimiter.ItemLimiter;
import com.wolfyxon.itemlimiter.ItemLimiterCommand;
import com.wolfyxon.itemlimiter.ItemMgr;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DataSizeCommand extends ItemLimiterCommand {

    public DataSizeCommand(ItemLimiter plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( !(sender instanceof Player)){
            sendMsg("This command can only be run by a player.",sender);
            return true;
        }
        if(!ConfigMgr.hasPermission("size",sender)){
            sendMsg(getConfig().getMessage("noPermission"),sender);
            return true;
        }

        Player plr = (Player) sender;
        ItemStack item = plr.getInventory().getItemInMainHand();

        if(item == null){
            sendMsg("You're not holding any item.",sender);
            return true;
        }

        sendMsg("Size of "+item.getType().toString()+": "+String.valueOf(ItemMgr.getSize(item)),sender);

        return true;
    }
}
