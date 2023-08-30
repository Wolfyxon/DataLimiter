package com.wolfyxon.itemlimiter.listeners;

import com.wolfyxon.itemlimiter.ConfigMgr;
import com.wolfyxon.itemlimiter.ItemLimiter;
import com.wolfyxon.itemlimiter.ItemMgr;
import com.wolfyxon.itemlimiter.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class PlayerEvents  implements Listener {
    ItemLimiter plugin;

    public PlayerEvents(ItemLimiter plugin) {
        this.plugin = plugin;
    }

    public ConfigMgr getConfig(){
        return plugin.configMgr;
    }

    public ItemMgr getItemMgr(){
        return plugin.itemMgr;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player plr = e.getPlayer();
        int count = getItemMgr().processPlayer(plr);
        if(count > 0){
            plr.sendMessage(getConfig().getMessage("itemsRemoved").replace("{count}",String.valueOf(count)));
        }
    }

    /*
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        //TODO: Re-add item scanning and removing on inventory click. (Incorrect player's inventory timing was causing issues so I removed it until I figure out a better way)
    }*/

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(!getConfig().getItemScanningFeatureEnabled("scanOnInteract")) return;
        ItemStack item = e.getItem();
        if(item == null) return;
        Player plr = e.getPlayer();
        if(getItemMgr().processItem(item,plr)){
            plr.sendMessage(getConfig().getMessage("itemRemoved").replace("{itemName}",item.getType().toString()));
        }
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent e){
        if(!getConfig().getItemScanningFeatureEnabled("scanOnPickup")) return;
        Entity entity = e.getEntity();
        if(!(entity instanceof Player)) return;
        Player plr = (Player) entity;
        Item itemEntity = e.getItem();
        if(getItemMgr().itemExceedsLimit(itemEntity)){
            plr.sendMessage(getConfig().getMessage("cantPickup").replace("{itemName}",itemEntity.getItemStack().getType().toString()));
            itemEntity.remove();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e){
        if(!getConfig().getItemScanningFeatureEnabled("scanOnDrop")) return;
        Player plr = e.getPlayer();
        Item itemEntity = e.getItemDrop();
        if(getItemMgr().itemExceedsLimit(itemEntity)){
            plr.sendMessage(getConfig().getMessage("cantDrop").replace("{itemName}",itemEntity.getItemStack().getType().toString()));
            itemEntity.remove();
        }
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent e){
        if(!getConfig().getFeatureEnabled("bookTrimming")) return;
        Player plr = e.getPlayer();
        BookMeta newMeta = e.getNewBookMeta();
        ItemStack item = ItemMgr.getItemInSlot(plr,e.getSlot());
        if(getItemMgr().bookExceedsPageLimit(newMeta)){
            BookMeta processedMeta = getItemMgr().processBookMeta(newMeta);
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
