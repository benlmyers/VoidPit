package com.characterlim.voidpit;

import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class VoidPitPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().info("=-=-=-=-= Void Hole =-=-=-=-=");
        this.getLogger().info("Created for Characterlim Ages");
        this.getLogger().info("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        TabExecutor tabExecutor = new PitCommand(this);
        this.getCommand("pit").setExecutor(tabExecutor);
        this.getCommand("pit").setTabCompleter(tabExecutor);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Disabling Void Hole...");
    }
}
