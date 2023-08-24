package com.wolfyxon.itemlimiter.listeners;

import com.wolfyxon.itemlimiter.ConfigMgr;
import com.wolfyxon.itemlimiter.ItemLimiter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerEvents  implements Listener {
    ItemLimiter plugin;
    ItemStack lastItem;
    EquipmentSlot lastHand;

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
    public void onPlayerPickupItem(EntityPickupItemEvent e){
        Entity entity = e.getEntity();
        if(!(entity instanceof Player)) return;
        Player plr = (Player) entity;
        Item item = e.getItem();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        lastHand = e.getHand();
        lastItem = e.getItem();
    }

    public void reAddHandItem(ItemStack item, Player player){
        if(item == null) return;
        PlayerInventory inv = player.getInventory();
        ItemStack im = inv.getItemInMainHand();
        ItemStack io = inv.getItemInOffHand();
        if(im !=null && item.isSimilar(im)){
            inv.setItemInMainHand(null);
            inv.setItemInMainHand(item);
            return;
        }
        if (io !=null && item.isSimilar(io)) {
            inv.setItemInOffHand(null);
            inv.setItemInOffHand(item);
            return;
        }
        inv.remove(item);
        inv.addItem(item);
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent e){
        Player plr = e.getPlayer();
        BookMeta newMeta = e.getNewBookMeta();

        if(newMeta.getPageCount() > getConfig().getMaxBookPages()){
            List<String> pages = newMeta.getPages();
            List<String> newPages = new ArrayList<>();
            for(int i=0;i<newMeta.getPageCount()-1;i++){
                if(i+1 <= getConfig().getMaxBookPages()){
                    newPages.add(pages.get(i));
                }
            }
            newMeta.setPages(newPages);
            e.setNewBookMeta(newMeta);
            reAddHandItem(lastItem,plr);
            //e.setCancelled(true);
        }
    }


}
