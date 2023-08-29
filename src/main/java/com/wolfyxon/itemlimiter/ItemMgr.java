package com.wolfyxon.itemlimiter;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemMgr {
    ItemLimiter plugin;

    public ItemMgr(ItemLimiter plugin){
        this.plugin = plugin;
    }

    ConfigMgr getConfig(){
        return plugin.configMgr;
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


    public static boolean isBook(ItemStack item){
        return item.getType() == Material.WRITABLE_BOOK || item.getType() == Material.WRITTEN_BOOK;
    }

    public boolean itemExceedsLimit(ItemStack item){
        return getSize(item) > getConfig().getMaxDataSize();
    }

    public boolean itemExceedsLimit(Item itemEntity){ return itemExceedsLimit(itemEntity.getItemStack()); }

    public static int getSize(ConfigurationSerializable serializable){
        return serializable.serialize().toString().getBytes().length;
    }
    public static ItemStack getItemInSlot(PlayerInventory inventory, int slot) { //supports offhand
        if(slot == -1) return inventory.getItemInOffHand();
        return inventory.getItem(slot);
    }
    public static ItemStack getItemInSlot(Player player, int slot){
        return getItemInSlot(player.getInventory(),slot);
    }

    public static boolean hasItem(PlayerInventory inventory, ItemStack item){
        if(inventory.getItemInOffHand().equals(item)) return true;
        return inventory.contains(item);
    }

    public static void removeItem(PlayerInventory inventory, ItemStack item){
        if(inventory.getItemInOffHand().equals(item)) inventory.setItemInOffHand(null);
        inventory.remove(item);
    }

    public static void removeItem(PlayerInventory inventory, int slot){
        if(slot == -1) inventory.setItemInOffHand(null);
        else inventory.setItem(slot,null);
    }
}
