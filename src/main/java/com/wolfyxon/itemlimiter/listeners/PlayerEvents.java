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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.BookMeta;

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
    public void onPlayerPickupItem(EntityPickupItemEvent e){
        Entity entity = e.getEntity();
        if(!(entity instanceof Player)) return;
        Player plr = (Player) entity;
        Item item = e.getItem();
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent e){
        Player plr = e.getPlayer();
        BookMeta newMeta = e.getNewBookMeta();

    }


}
