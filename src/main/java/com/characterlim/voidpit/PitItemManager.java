package com.characterlim.voidpit;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PitItemManager {

    private final VoidPitPlugin plugin;
    private final List<Material> items = new ArrayList<>();

    public PitItemManager(VoidPitPlugin instance) {
        this.plugin = instance;
        List<String> itemStrings = (List<String>) plugin.getConfig().getList("accepted-items");
        if(itemStrings != null) {
            for(String itemString: itemStrings) items.add(Material.getMaterial(itemString));
        }
    }

    public void setItem(Player player) {
        items.clear();
        items.add(getItem(player));
        player.sendMessage("§bVoid Pit accepted item set to §9" + getItem(player).toString().toLowerCase());
        updateConfig();
    }

    public void addItem(Player player) {
        items.add(getItem(player));
        if(items.contains(getItem(player).toString().toLowerCase())) {
            player.sendMessage("§9" + getItem(player).toString().toLowerCase() + " §bis already a part of the accepted items list!");
            return;
        }
        player.sendMessage("§9" + getItem(player).toString().toLowerCase() + " §badded to Void Pit accepted items list.");
        updateConfig();
    }

    public void removeItem(Player player) {
        items.remove(getItem(player));
        player.sendMessage("§9" + getItem(player).toString().toLowerCase() + " §bremoved from Void Pit accepted items list.");
        updateConfig();
    }

    public void resetItems(Player player) {
        items.clear();
        player.sendMessage("§bVoid Pit accepted items have been reset.");
        updateConfig();
    }

    public void listItems(Player player) {
        if(items.size() == 0) {
            player.sendMessage("§bYour pit does not accept any items. Use §e/pit item add §bto add new items.");
        }
        StringBuilder message = new StringBuilder("§bVoid Pit accepted items (" + items.size() + "): ");
        for(Material material : items) {
            message.append("§9").append(material.toString().toLowerCase()).append("§b, ");
        }
        message.setLength(message.length() - 2);
        player.sendMessage(message.toString());
    }

    private Material getItem(Player player) {
        return player.getInventory().getItemInMainHand().getType();
    }

    private void updateConfig() {
        List<String> itemStrings = new ArrayList<String>();
        for(Material item : items) itemStrings.add(item.toString());
        plugin.getConfig().addDefault("accepted-items", itemStrings);
        plugin.saveConfig();
    }
}
