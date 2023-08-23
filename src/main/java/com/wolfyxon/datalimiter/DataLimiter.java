package com.wolfyxon.datalimiter;

import com.wolfyxon.datalimiter.listeners.PlayerEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class DataLimiter extends JavaPlugin {

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
