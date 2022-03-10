package com.selfcoders.collection;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        FileConfiguration config = getConfig();
        PluginManager pluginManager = getServer().getPluginManager();

        if (config.getBoolean("ChestClick2Inventory.enabled")) {
            pluginManager.registerEvents(new ChestClick2Inventory(), this);
        }
    }
}