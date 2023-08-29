package com.wolfyxon.itemlimiter.listeners;

import com.wolfyxon.itemlimiter.ConfigMgr;
import com.wolfyxon.itemlimiter.ItemLimiter;
import com.wolfyxon.itemlimiter.ItemMgr;
import com.wolfyxon.itemlimiter.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Arrays;
import java.util.HashMap;

public class PlayerEvents  implements Listener {
    ItemLimiter plugin;

    public PlayerEvents(ItemLimiter plugin) {
        this.plugin = plugin;
    }

    public ConfigMgr getConfig(){
        return plugin.configMgr;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player plr = e.getPlayer();

    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent e){
        Entity entity = e.getEntity();
        if(!(entity instanceof Player)) return;
        Player plr = (Player) entity;
        Item itemEntity = e.getItem();
        ItemStack item = itemEntity.getItemStack();

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){

    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e){
        Player plr = e.getPlayer();
        Item itemEntity = e.getItemDrop();
        ItemStack item = itemEntity.getItemStack();

    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent e){
        Player plr = e.getPlayer();
        BookMeta newMeta = e.getNewBookMeta();
        ItemStack item = ItemMgr.getItemInSlot(plr,e.getSlot());
        plugin.getLogger().info(plr.getInventory().getItemInMainHand().toString());
        if(plugin.itemMgr.bookExceedsPageLimit(newMeta)){
            BookMeta processedMeta = plugin.itemMgr.processBookMeta(newMeta);
            e.setNewBookMeta(processedMeta);
            item.setItemMeta(processedMeta);
            Utils.reAddHandItem(item,plr);
            plr.sendMessage(getConfig().getMessage("bookSaveCancelledPages")
                    .replace("{max}",String.valueOf(getConfig().getMaxBookPages()))
            );
            e.setCancelled(true);
        }
    }


}
