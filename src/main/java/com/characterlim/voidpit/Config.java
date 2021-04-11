package com.characterlim.voidpit;

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
            plugin.getConfig().set("region-corner1", pos1);
            plugin.getConfig().set("region-corner2", pos2);
            plugin.saveConfig();
        }
    }

    public static class Item {

        public static List<Material> items = new ArrayList<>();

        public static void load() {
            List<String> itemStrings = (List<String>) plugin.getConfig().getList("accepted-items");
            if(itemStrings != null) {
                for(String itemString: itemStrings) items.add(Material.getMaterial(itemString));
            }
        }

        public static void save() {
            List<String> itemStrings = new ArrayList<>();
            for(Material item : Config.Item.items) itemStrings.add(item.toString());
            plugin.getConfig().set("accepted-items", itemStrings);
            plugin.saveConfig();
        }
    }

    public static class Hologram {

        public static Location pos;

        public static void load() {
            pos = plugin.getConfig().getLocation("pos-hologram");
        }

        public static void save() {
            plugin.getConfig().set("pos-hologram", pos);
            plugin.saveConfig();
        }
    }

    public static class Energy {

        public static int energy = 0;
        public static int maxEnergy = 100000;

        public static void load() {
            energy = plugin.getConfig().getInt("energy");
            maxEnergy = plugin.getConfig().getInt("max-energy");
        }

        public static void save() {
            plugin.getConfig().set("energy", energy);
            plugin.getConfig().set("max-energy", maxEnergy);
        }
    }

    public static void load() {
        Region.load();
        Item.load();
        Hologram.load();
        Energy.load();
    }

    public static void save() {
        Region.save();
        Item.save();
        Hologram.save();
        Energy.save();
    }
}
