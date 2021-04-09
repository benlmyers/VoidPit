package com.characterlim.voidpit;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PitRegionManager {

    private final VoidPitPlugin plugin;
    private WorldEditPlugin worldEditPlugin = null;
    private Location pos1 = null;
    private Location pos2 = null;

    public PitRegionManager(VoidPitPlugin instance) {
        this.plugin = instance;
        this.worldEditPlugin = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        this.pos1 = instance.getConfig().getLocation("region-corner1");
        this.pos2 = instance.getConfig().getLocation("region-corner2");
    }

    public void setRegion(Player player) {
        try {
            LocalSession session = worldEditPlugin.getSession(player);
            World world = session.getSelectionWorld();
            Region region = worldEditPlugin.getSession(player).getSelection(world);
            BlockVector3 v1 = region.getMinimumPoint();
            BlockVector3 v2 = region.getMaximumPoint();
            this.pos1 = new Location(player.getWorld(), (double) v1.getX(), (double) v1.getY(), (double) v1.getZ());
            this.pos2 = new Location(player.getWorld(), (double) v2.getX(), (double) v2.getY(), (double) v2.getZ());
            player.sendMessage("§bRegion set! Corner 1: §9" + this.pos1.toString() + "§b | Corner 2: §9" + this.pos2.toString());
            updateConfig();
        } catch(IncompleteRegionException e) {
            player.sendMessage("§bYou haven't selected a complete region! Ensure a complete selection is selected, then try again.");
        }
    }

    public void showRegion(Player player) {
        if(pos1 != null && pos2 != null) {
            player.sendMessage("§bCorner 1: §9" + this.pos1.toString() + "§b | Corner 2: §9" + this.pos2.toString());
        }
    }

    private void updateConfig() {
        plugin.getConfig().addDefault("region-corner1", this.pos1);
        plugin.getConfig().addDefault("region-corner2", this.pos2);
        plugin.saveConfig();
    }
}
