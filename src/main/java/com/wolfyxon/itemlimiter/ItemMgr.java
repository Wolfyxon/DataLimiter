package com.wolfyxon.itemlimiter;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

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
}
