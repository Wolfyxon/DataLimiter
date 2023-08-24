package com.wolfyxon.itemlimiter.listeners;

import com.wolfyxon.itemlimiter.ConfigMgr;
import com.wolfyxon.itemlimiter.ItemLimiter;
import com.wolfyxon.itemlimiter.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerEvents  implements Listener {
    ItemLimiter plugin;
    HashMap<Player,ItemStack> lastItems = new HashMap<>();

    public PlayerEvents(ItemLimiter plugin) {
        this.plugin = plugin;
    }

    public ConfigMgr getConfig(){
        return plugin.configMgr;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player plr = e.getPlayer();
        int removed = plugin.itemMgr.processPlayer(plr);
        if(removed>0){
            plr.sendMessage(getConfig().getMessage("itemsRemoved").replace("{count}",String.valueOf(removed)));
        }
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent e){
        Entity entity = e.getEntity();
        if(!(entity instanceof Player)) return;
        Player plr = (Player) entity;
        Item itemEntity = e.getItem();
        ItemStack item = itemEntity.getItemStack();
        if (plugin.itemMgr.processItem(item,plr)){
            e.setCancelled(true);
            itemEntity.remove();
            plr.sendMessage(getConfig().getMessage("itemRemoved").replace("{itemName}",item.getType().toString()));
        }
    }

    public boolean itemAction(ItemStack item, Player player, Cancellable cancellable){
        if (plugin.itemMgr.processItem(item,player)){
            cancellable.setCancelled(true);
            player.sendMessage(getConfig().getMessage("itemRemoved").replace("{itemName}",item.getType().toString()));
        }
        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        itemAction(e.getCurrentItem(),(Player) e.getWhoClicked(), e);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e){
        itemAction(e.getCursor(),(Player) e.getWhoClicked(),e);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        lastItems.put(e.getPlayer(), e.getItem());
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

        if(plugin.itemMgr.bookExceedsPageLimit(newMeta)){
            BookMeta processedMeta = plugin.itemMgr.processBookMeta(newMeta);
            e.setNewBookMeta(processedMeta);
            Utils.reAddHandItem(lastItems.get(plr),plr);

            e.setCancelled(true);
        }
    }


}
