package com.wolfyxon.itemlimiter;

import com.wolfyxon.itemlimiter.listeners.PlayerEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemLimiter extends JavaPlugin {

    public PlayerEvents playerEvents = new PlayerEvents(this);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(playerEvents, this);

        getLogger().info("Running");
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting down");
    }
}
