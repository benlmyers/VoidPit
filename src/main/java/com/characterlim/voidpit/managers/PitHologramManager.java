package com.characterlim.voidpit.managers;

import com.characterlim.voidpit.Config;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.characterlim.voidpit.VoidPitPlugin;
import org.bukkit.entity.Player;

public class PitHologramManager {

    private final VoidPitPlugin plugin;
    private Hologram hologram;

    public PitHologramManager(VoidPitPlugin instance) {
        this.plugin = instance;

    }

    public void setPosition(Player player) {
        Config.Hologram.pos = player.getLocation();
        Config.Hologram.save();
        player.sendMessage("§bHologram location set to §9 " + Config.Hologram.pos.toString() + "§b.");
    }

    public void showPosition(Player player) {
        player.sendMessage("§bLocation: §9" + Config.Hologram.pos.toString());
    }
}
