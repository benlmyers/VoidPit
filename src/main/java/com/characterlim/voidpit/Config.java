package com.characterlim.voidpit;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class Config {

    public static VoidPitPlugin plugin;

    public static class Region {

        public static Location pos1, pos2;

        public static void load() {
            pos1 = plugin.getConfig().getLocation("region-corner1");
            pos2 = plugin.getConfig().getLocation("region-corner2");
        }
    }

    public static class Item {

        public static List<Material> items;

        public static void load() {
            List<String> itemStrings = (List<String>) plugin.getConfig().getList("accepted-items");
            if(itemStrings != null) {
                for(String itemString: itemStrings) items.add(Material.getMaterial(itemString));
            }
        }
    }

    public static void load() {
        Region.load();
        Item.load();
    }
}
