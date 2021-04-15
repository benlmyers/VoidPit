package com.characterlim.voidpit.listeners;

import com.characterlim.voidpit.CompletionHandler;
import com.characterlim.voidpit.Config;
import com.characterlim.voidpit.ParticleAnimator;
import com.characterlim.voidpit.VoidPitPlugin;
import com.characterlim.voidpit.managers.PitHologramManager;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Location;
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

    private final VoidPitPlugin plugin;
    private final List<Material> acceptedItems = new ArrayList<>();
    private ParticleAnimator particleAnimator;
    private Region pitRegion = null;

    private static final long TPS = 20;
    private static final long CHECK_DELAY = 2 * TPS;
    private static final long CHECK_PERIOD = TPS;
    private static final int MAX_CHECKS = 6;

    public DropListener(VoidPitPlugin instance) {
        this.plugin = instance;
        fetchItemsAndRegion();
    }

    @EventHandler
    public void dropEvent(PlayerDropItemEvent event) {
        final int[] count = {0};
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                count[0] = checkDrop(event, this, count[0]);
            }
        };
        task.runTaskTimer(plugin, CHECK_DELAY, CHECK_PERIOD);
    }

    public void fetchItemsAndRegion() {
        List<String> itemStrings = (List<String>) plugin.getConfig().getList("accepted-items");
        if(itemStrings != null) {
            for(String itemString: itemStrings) acceptedItems.add(Material.getMaterial(itemString));
        }

        Location l1 = Config.Region.pos1;
        Location l2 = Config.Region.pos2;
        if(l1 != null && l2 != null) {
            BlockVector3 v1 = BlockVector3.at(l1.getBlockX(), l1.getBlockY(), l1.getBlockZ());
            BlockVector3 v2 = BlockVector3.at(l2.getBlockX(), l2.getBlockY(), l2.getBlockZ());
            this.pitRegion = new CuboidRegion(v1, v2);
            this.particleAnimator = new ParticleAnimator(l1.getWorld(), plugin);
        }
    }

    private int checkDrop(PlayerDropItemEvent event, BukkitRunnable task, int count) {
        Player player = event.getPlayer();
        ItemStack stack = event.getItemDrop().getItemStack();

        if (acceptedItems.contains(stack.getType())) {
            handleStack(player, event);
            task.cancel();
        } else count++;

        if(count >= MAX_CHECKS) task.cancel();
        return count;
    }

    private void handleStack(Player player, PlayerDropItemEvent event) {
        if(pitRegion != null) {
            Location dropLocation = event.getItemDrop().getLocation();
            BlockVector3 dropPosition = BlockVector3.at(dropLocation.getBlockX(), dropLocation.getBlockY(), dropLocation.getBlockZ());
            ItemStack stack = event.getItemDrop().getItemStack();
            if(this.pitRegion.contains(dropPosition)) {
                player.sendMessage("§bYou've sacrificed §9" + stack.getAmount() + "§b items to the Pit!");
                particleAnimator.animateItemDestroy(dropLocation);
                particleAnimator.animateAscend(dropLocation, new CompletionHandler() {
                    @Override
                    public void onCompletion() {
                        handleParticleFinished(player, stack.getAmount());
                    }
                });
                event.getItemDrop().remove();
            }
        }
    }

    private void handleParticleFinished(Player player, int amount) {
        Config.Energy.energy += amount;
        if(Config.Energy.userEnergy.containsKey(player.getName())) {
            try {
                Config.Energy.userEnergy.put(player.getName(), Config.Energy.userEnergy.get(player.getName()) + amount);
            } catch(Exception e) {
                Config.Energy.userEnergy.put(player.getName(), amount);
            }
        } else {
            Config.Energy.userEnergy.put(player.getName(), amount);
        }
        Config.Energy.save();
        PitHologramManager.refresh();
    }
}
