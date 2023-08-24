package com.wolfyxon.itemlimiter.listeners;

import com.wolfyxon.itemlimiter.ConfigMgr;
import com.wolfyxon.itemlimiter.ItemLimiter;
import com.wolfyxon.itemlimiter.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
        lastItems.put(e.getPlayer(), e.getItem());
    }

    public boolean bookExceedsPageLimit(BookMeta meta){
        return meta.getPageCount() > getConfig().getMaxBookPages();
    }

    public BookMeta processBookMeta(BookMeta meta){
        if(!bookExceedsPageLimit(meta)) return meta;

        List<String> pages = meta.getPages();
        List<String> newPages = new ArrayList<>();
        for(int i=0;i<meta.getPageCount()-1;i++){
            if(i+1 <= getConfig().getMaxBookPages()){
                newPages.add(pages.get(i));
            }
        }
        meta.setPages(newPages);

        return meta;
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent e){
        Player plr = e.getPlayer();
        BookMeta newMeta = e.getNewBookMeta();

        if(bookExceedsPageLimit(newMeta)){
            BookMeta processedMeta = processBookMeta(newMeta);
            e.setNewBookMeta(processedMeta);
            Utils.reAddHandItem(lastItems.get(plr),plr);
        }
    }


}
