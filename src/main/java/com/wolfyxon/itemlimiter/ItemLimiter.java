package com.wolfyxon.itemlimiter;

import com.wolfyxon.itemlimiter.commands.DataSizeCommand;
import com.wolfyxon.itemlimiter.commands.MainCommand;
import com.wolfyxon.itemlimiter.listeners.PlayerEvents;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemLimiter extends JavaPlugin {

    public ConfigMgr configMgr = new ConfigMgr(this);
    public ItemMgr itemMgr = new ItemMgr(this);
    public PlayerEvents playerEvents = new PlayerEvents(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configMgr.load();
        getServer().getPluginManager().registerEvents(playerEvents, this);

        registerCommand("itemlimiter", new MainCommand(this));
        registerCommand("datasize", new DataSizeCommand(this));

        getLogger().info("Running");
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting down");
    }

    public void registerCommand(String name, ItemLimiterCommand handler){
        PluginCommand command = getCommand(name);
        command.setExecutor(handler);
        if(Utils.hasMethod(handler,"onTabComplete")) command.setTabCompleter(handler);
    }
}
