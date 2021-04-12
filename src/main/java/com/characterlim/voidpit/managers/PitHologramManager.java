package com.characterlim.voidpit.managers;

import com.characterlim.voidpit.Config;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.characterlim.voidpit.VoidPitPlugin;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PitHologramManager {

    private final VoidPitPlugin plugin;
    private Hologram hologram;
    private TextLine line1, line2, line3, line4;
    private ItemLine line5;
    private static final int PROGRESS_BAR_LENGTH = 18;

    public PitHologramManager(VoidPitPlugin instance) {
        this.plugin = instance;
        refresh();
    }

    public void refresh() {
        hologram = HologramsAPI.createHologram(plugin, Config.Hologram.pos);
        line1 = hologram.appendTextLine("§d§lTHE PIT");
        line2 = hologram.appendTextLine("§eAccepted Items: " + acceptedItemStrings());
        line3 = hologram.appendTextLine("§eProgress: " + progressString());
        line4 = hologram.appendTextLine(progressBar());
        line5 = hologram.appendItemLine(new ItemStack(Material.SNOW_BLOCK));
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

    private String acceptedItemStrings() {
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
    }

    private String progressString() {
        double progress = Config.Energy.energy;
        double max = Config.Energy.maxEnergy;
        double ratio = progress / max;
        int percent = (int) (1000.0 * ratio);
        return "§a" + progress + "§7/§r" + max + " §7(§a" + percent + "§7)";
    }

    private String progressBar() {
        double progress = Config.Energy.energy;
        double max = Config.Energy.maxEnergy;
        double ratio = progress / max;
        double color = ratio * PROGRESS_BAR_LENGTH;
        String str = "";
        for(int i = 0; i < PROGRESS_BAR_LENGTH; i++) {
            if(i >= color) str += "§a§l|";
            else str += "§7§l|";
        }
        return str;
    }
}
