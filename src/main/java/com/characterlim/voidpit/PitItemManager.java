package com.characterlim.voidpit;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PitItemManager {

    private final VoidPitPlugin plugin;
    private final List<Material> items;

    public PitItemManager(VoidPitPlugin instance) {
        this.plugin = instance;
        List<Material> configItems = (List<Material>) plugin.getConfig().get("accepted-items");
        if(configItems == null) this.items = new ArrayList<>();
        else this.items = configItems;
    }

    public void setItem(Player player) {
        items.clear();
        items.add(getItem(player));
        updateConfig();
    }

    public void addItem(Player player) {
        items.add(getItem(player));
        updateConfig();
    }

    public void removeItem(Player player) {
        items.remove(getItem(player));
        updateConfig();
    }

    public void resetItems() {
        items.clear();
        updateConfig();
    }

    public void listItems(Player player) {
        if(items.size() == 0) {
            player.sendMessage("§bYour pit does not accept any items. Use §e/pit item add §bto add new items.");
        }
        StringBuilder message = new StringBuilder("§bVoid Pit accepted items: ");
        for(Material material : items) {
            message.append("§9").append(material.toString().toLowerCase()).append("§b, ");
        }
        message = new StringBuilder(message.substring(message.length() - 2));
        player.sendMessage(message.toString());
    }

    private Material getItem(Player player) {
        return player.getInventory().getItemInMainHand().getType();
    }

    private void updateConfig() {
        plugin.getConfig().addDefault("accepted-items", items);
        plugin.saveConfig();
    }
}
