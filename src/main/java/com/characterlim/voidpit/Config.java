package com.characterlim.voidpit;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static VoidPitPlugin plugin;

    public static class Region {

        public static Location pos1, pos2;

        public static void load() {
            pos1 = plugin.getConfig().getLocation("region-corner1");
            pos2 = plugin.getConfig().getLocation("region-corner2");
        }

        public static void save() {
            plugin.getConfig().addDefault("region-corner1", Config.Region.pos1);
            plugin.getConfig().addDefault("region-corner2", Config.Region.pos2);
            plugin.saveConfig();
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

        public static void save() {
            List<String> itemStrings = new ArrayList<String>();
            for(Material item : Config.Item.items) itemStrings.add(item.toString());
            plugin.getConfig().addDefault("accepted-items", itemStrings);
            plugin.saveConfig();
        }
    }

    public static void load() {
        Region.load();
        Item.load();
    }

    public static void save() {
        Region.save();
        Item.save();
    }
}
