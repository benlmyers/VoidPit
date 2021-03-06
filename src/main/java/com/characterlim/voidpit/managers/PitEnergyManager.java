package com.characterlim.voidpit.managers;

import com.characterlim.voidpit.Config;
import com.characterlim.voidpit.VoidPitPlugin;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PitEnergyManager {

    private final VoidPitPlugin plugin;

    public PitEnergyManager(VoidPitPlugin instance) {
        this.plugin = instance;
    }

    public void setMaxEnergy(Player player, int amount) {
        Config.Energy.maxEnergy = amount;
        Config.Energy.save();
        player.sendMessage("§bMax energy set to §9" + amount + "§b.");
        PitHologramManager.refresh();
    }

    public void setEnergy(Player player, int amount) {
        Config.Energy.energy = amount;
        Config.Energy.save();
        player.sendMessage("§bEnergy to set §9" + amount + "§b.");
    }
}
