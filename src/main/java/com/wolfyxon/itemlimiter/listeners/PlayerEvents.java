package com.wolfyxon.itemlimiter.listeners;

import com.wolfyxon.itemlimiter.ConfigMgr;
import com.wolfyxon.itemlimiter.ItemLimiter;
import com.wolfyxon.itemlimiter.ItemMgr;
import com.wolfyxon.itemlimiter.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryAction;
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
        if(!getConfig().getItemScanningFeatureEnabled("scanOnJoin")) return;
        Player plr = e.getPlayer();
        int count = getItemMgr().processPlayer(plr);
        if(count > 0){
            plr.sendMessage(getConfig().getMessage("itemsRemoved").replace("{count}",String.valueOf(count)));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(!getConfig().getItemScanningFeatureEnabled("scanOnInventoryClick")) return;

        if(e.getAction() == InventoryAction.NOTHING) return;
        if(e.getAction() == InventoryAction.COLLECT_TO_CURSOR) return;
        if(e.getAction() == InventoryAction.DROP_ALL_CURSOR) return;
        if(e.getAction() == InventoryAction.DROP_ALL_SLOT) return;
        if(e.getAction() == InventoryAction.DROP_ONE_CURSOR) return;
        if(e.getAction() == InventoryAction.DROP_ONE_SLOT) return;

        ItemStack current = e.getCurrentItem();
        if(current == null) return;
        Player plr = (Player) e.getWhoClicked();
        if(getItemMgr().itemExceedsLimit(current)){
            getConfig().sendItemRemoved(plr,current);
            e.setCurrentItem(null);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(!getConfig().getItemScanningFeatureEnabled("scanOnInteract")) return;
        ItemStack item = e.getItem();
        if(item == null) return;
        Player plr = e.getPlayer();
        if(getItemMgr().processItem(item,plr)){
            if(ItemMgr.isBook(item)){
                // this closes the book GUI by overwriting it with inventory GUI then immediately closing it.
                plr.openInventory(Bukkit.createInventory(null,9));
                plr.closeInventory();
            }
            getConfig().sendItemRemoved(plr,item);
            e.setCancelled(true);
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
            if(getConfig().getMode().equals("remove")){
                itemEntity.remove();
                e.setCancelled(true);
            } else if (getConfig().getMode().equals("clearmeta")) {
                itemEntity.setItemStack(ItemMgr.getWithoutMeta(itemEntity.getItemStack()));
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e){
        if(!getConfig().getItemScanningFeatureEnabled("scanOnDrop")) return;
        Player plr = e.getPlayer();
        Item itemEntity = e.getItemDrop();
        if(getItemMgr().itemExceedsLimit(itemEntity)){
            plr.sendMessage(getConfig().getMessage("cantDrop").replace("{itemName}",itemEntity.getItemStack().getType().toString()));
            if(getConfig().getMode().equals("remove")){
                itemEntity.remove();
            } else if (getConfig().getMode().equals("clearmeta")) {
                itemEntity.setItemStack(ItemMgr.getWithoutMeta(itemEntity.getItemStack()));
            }
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
            plr.sendMessage(getConfig().getMessage("bookTrimmed")
                    .replace("{max}",String.valueOf(getConfig().getMaxBookPages()))
            );
            e.setCancelled(true);
        }
        if(getConfig().getItemScanningFeatureEnabled("scanOnBookEdit")){
            item = ItemMgr.getItemInSlot(plr,e.getSlot()); //re-get item in case it was trimmed
            if(getItemMgr().itemExceedsLimit(item)){
                if(getConfig().getMode().equals("remove")){
                    ItemMgr.removeItem(plr.getInventory(),e.getSlot());
                } else if (getConfig().getMode().equals("clearmeta")) {
                    plr.getInventory().setItem(e.getSlot(),ItemMgr.getWithoutMeta(item));
                }

                getConfig().sendItemRemoved(plr,item);
            }
        }
    }


}
