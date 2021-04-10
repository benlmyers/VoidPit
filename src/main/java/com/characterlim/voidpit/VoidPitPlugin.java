package com.characterlim.voidpit;

import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class VoidPitPlugin extends JavaPlugin {

    private FileConfiguration config = getConfig();
    private DropListener dropListener = new DropListener(this);

    @Override
    public void onEnable() {
        this.getLogger().info("=-=-=-=-= Void Hole =-=-=-=-=");
        this.getLogger().info("Created for Characterlim Ages");
        this.getLogger().info("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        initConfig();

        TabExecutor tabExecutor = new PitCommand(this);
        this.getCommand("pit").setExecutor(tabExecutor);
        this.getCommand("pit").setTabCompleter(tabExecutor);

        getServer().getPluginManager().registerEvents(dropListener, this);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Disabling Void Hole...");
    }

    private void initConfig() {
        config.addDefault("config-version", "1.0");
        config.options().copyDefaults(true);
        saveConfig();
        Config.plugin = this;
        Config.load();
    }
}
