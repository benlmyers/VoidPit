package com.characterlim.voidpit;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PitItemManager {

    private List<Material> items = new ArrayList<Material>();

    public PitItemManager() {}

    public void setItem(Player player) {
        items.clear();
        items.add(getItem(player));
    }

    public void addItem(Player player) {
        items.add(getItem(player));
    }

    public void removeItem(Player player) {
        items.remove(getItem(player));
    }

    public void resetItems() {
        items.clear();
    }

    public void listItems(Player player) {
        String message = "§bVoid Pit accepted items: ";
        for(Material material : items) {
            message += "§9" + material.toString().toLowerCase() + "§b, ";
        }
        message = message.substring(message.length() - 2);
        player.sendMessage(message);
    }

    private Material getItem(Player player) {
        return player.getInventory().getItemInMainHand().getType();
    }
}
