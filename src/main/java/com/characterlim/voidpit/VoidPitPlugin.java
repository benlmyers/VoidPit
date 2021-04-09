package com.characterlim.voidpit;

import org.bukkit.plugin.java.JavaPlugin;

public class VoidPitPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().info("=-=-=-=-= Void Hole =-=-=-=-=");
        this.getLogger().info("Created for Characterlim Ages");
        this.getLogger().info("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }
    @Override
    public void onDisable() {
        this.getLogger().info("Disabling Void Hole...");
    }
}
