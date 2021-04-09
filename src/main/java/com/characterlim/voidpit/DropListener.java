package com.characterlim.voidpit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class DropListener implements Listener {

    VoidPitPlugin plugin;
    List<Material> acceptedItems = new ArrayList<>();

    private static final long TPS = 20;
    private static final long CHECK_DELAY = 2 * TPS;
    private static final long CHECK_PERIOD = 1 * TPS;

    public DropListener(VoidPitPlugin instance) {
        this.plugin = instance;
        List<String> itemStrings = (List<String>) plugin.getConfig().getList("accepted-items");
        if(itemStrings != null) {
            for(String itemString: itemStrings) acceptedItems.add(Material.getMaterial(itemString));
        }
    }

    @EventHandler
    public void dropEvent(PlayerDropItemEvent event) {
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                checkDrop(event, this);
            }
        };

        task.runTaskTimer(plugin, CHECK_DELAY, CHECK_PERIOD);
        task.cancel();
    }

    private void checkDrop(PlayerDropItemEvent event, BukkitRunnable task) {
        Player player = event.getPlayer();
        ItemStack stack = event.getItemDrop().getItemStack();

        if (acceptedItems.contains(stack.getType())) {
            player.sendMessage("§bItem §9" + stack.getType().toString() + "§b is lying on block §9" + event.getItemDrop().getLocation().add(0, -1, 0).getBlock().getType().toString());
            task.cancel();
        } else {
            player.sendMessage("no accepted item drop found");
        }
    }
}
