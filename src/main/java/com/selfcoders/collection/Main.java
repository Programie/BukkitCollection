package com.selfcoders.collection;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new EventListener(), this);
    }
}