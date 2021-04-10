package com.characterlim.voidpit;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PitItemManager {

    private final VoidPitPlugin plugin;

    public PitItemManager(VoidPitPlugin instance) {
        this.plugin = instance;
    }

    public void setItem(Player player) {
        Config.Item.items.clear();
        Config.Item.items.add(getItem(player));
        player.sendMessage("§bVoid Pit accepted item set to §9" + getItem(player).toString().toLowerCase());
        updateConfig();
    }

    public void addItem(Player player) {
        Config.Item.items.add(getItem(player));
        if(Config.Item.items.contains(getItem(player).toString().toLowerCase())) {
            player.sendMessage("§9" + getItem(player).toString().toLowerCase() + " §bis already a part of the accepted items list!");
            return;
        }
        player.sendMessage("§9" + getItem(player).toString().toLowerCase() + " §badded to Void Pit accepted items list.");
        updateConfig();
    }

    public void removeItem(Player player) {
        Config.Item.items.remove(getItem(player));
        player.sendMessage("§9" + getItem(player).toString().toLowerCase() + " §bremoved from Void Pit accepted items list.");
        updateConfig();
    }

    public void resetItems(Player player) {
        Config.Item.items.clear();
        player.sendMessage("§bVoid Pit accepted items have been reset.");
        updateConfig();
    }

    public void listItems(Player player) {
        if(Config.Item.items.size() == 0) {
            player.sendMessage("§bYour pit does not accept any items. Use §e/pit item add §bto add new items.");
        }
        StringBuilder message = new StringBuilder("§bVoid Pit accepted items (" + Config.Item.items.size() + "): ");
        for(Material material : Config.Item.items) {
            message.append("§9").append(material.toString().toLowerCase()).append("§b, ");
        }
        message.setLength(message.length() - 2);
        player.sendMessage(message.toString());
    }

    public List<Material> getItems() {
        return Config.Item.items;
    }

    private Material getItem(Player player) {
        return player.getInventory().getItemInMainHand().getType();
    }

    private void updateConfig() {
        List<String> itemStrings = new ArrayList<String>();
        for(Material item : Config.Item.items) itemStrings.add(item.toString());
        plugin.getConfig().addDefault("accepted-items", itemStrings);
        plugin.saveConfig();
    }
}
