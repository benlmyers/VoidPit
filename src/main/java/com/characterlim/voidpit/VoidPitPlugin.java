package com.characterlim.voidpit;

import com.characterlim.voidpit.commands.PitCommand;
import com.characterlim.voidpit.listeners.DropListener;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class VoidPitPlugin extends JavaPlugin {

    private DropListener dropListener;

    @Override
    public void onEnable() {
        this.dropListener = new DropListener(this);

        this.getLogger().info("=-=-=-=-= Void Hole =-=-=-=-=");
        this.getLogger().info("Created for Characterlim Ages");
        this.getLogger().info("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        TabExecutor tabExecutor = new PitCommand(this);
        this.getCommand("pit").setExecutor(tabExecutor);
        this.getCommand("pit").setTabCompleter(tabExecutor);

        getServer().getPluginManager().registerEvents(dropListener, this);

        Config.plugin = this;
        Config.load();
    }

    @Override
    public void onDisable() {
        Config.save();
        this.getLogger().info("Disabling Void Hole...");
    }

    public void updateItemsOrRegion() {
        dropListener.fetchItemsAndRegion();
    }
}
