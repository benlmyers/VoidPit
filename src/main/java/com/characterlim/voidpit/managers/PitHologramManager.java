package com.characterlim.voidpit.managers;

import com.characterlim.voidpit.Config;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.characterlim.voidpit.VoidPitPlugin;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PitHologramManager {

    private static VoidPitPlugin plugin = null;
    private static Hologram hologram;
    private static TextLine line1, line2, line3, line4;
    private static ItemLine line5;
    private static final int PROGRESS_BAR_LENGTH = 18;

    public PitHologramManager(VoidPitPlugin instance) {
        plugin = instance;
        refresh();
    }

    public static void refresh() {
        if(hologram != null) {
            hologram.clearLines();
            hologram.delete();
            hologram = null;
        }
        Location spawnLoc = new Location(Config.Hologram.pos.getWorld(), Config.Hologram.pos.getX(), Config.Hologram.pos.getY(), Config.Hologram.pos.getZ());
        hologram = HologramsAPI.createHologram(plugin, spawnLoc.add(0, 1.2, 0));
        line1 = hologram.appendTextLine("§d§lTHE PIT");
        line2 = hologram.appendTextLine("§eAccepted Items: " + acceptedItemStrings());
        line3 = hologram.appendTextLine("§eProgress: " + progressString());
        line4 = hologram.appendTextLine(progressBar());
        line5 = hologram.appendItemLine(new ItemStack(Material.QUARTZ_BLOCK, 1));
    }

    public void setPosition(Player player) {
        Config.Hologram.pos = player.getLocation();
        Config.Hologram.save();
        player.sendMessage("§bHologram location set to §9 " + Config.Hologram.pos.toString() + "§b.");
        refresh();
    }

    public void showPosition(Player player) {
        player.sendMessage("§bLocation: §9" + Config.Hologram.pos.toString());
    }

    public void setLabel(Player player, String label) {
        Config.Hologram.label = label;
        Config.Hologram.save();
        player.sendMessage("§bHologram label set to: \"§9" + label + "§b\".");
        PitHologramManager.refresh();
    }

    private static String acceptedItemStrings() {
        if(Config.Hologram.label == null || Config.Hologram.label == "") {
            String str = "§b";
            if(Config.Item.items.size() > 0) {
                for (int i = 0; i < Config.Item.items.size() - 1; i++) {
                    str += Config.Item.items.get(i) + ", ";
                }
                str += Config.Item.items.get(Config.Item.items.size() - 1);
            } else {
                str = "§b(none)";
            }
            return str;
        } else {
            return "§b" + Config.Hologram.label;
        }
    }

    private static String progressString() {
        double progress = Config.Energy.energy;
        double max = Config.Energy.maxEnergy;
        double ratio = progress / max;
        int percent = (int) (10000.0 * ratio);
        float shownPercent = (float) (percent / 100.0);
        return "§a" + (int)progress + "§7/§r" + (int)max + " §7(§a" + shownPercent + "%§7)";
    }

    private static String progressBar() {
        double progress = Config.Energy.energy;
        double max = Config.Energy.maxEnergy;
        double ratio = progress / max;
        double color = ratio * PROGRESS_BAR_LENGTH;
        String str = "";
        for(int i = 0; i < PROGRESS_BAR_LENGTH; i++) {
            if(i < color) str += "§a§l|";
            else str += "§8§l|";
        }
        return str;
    }
}
