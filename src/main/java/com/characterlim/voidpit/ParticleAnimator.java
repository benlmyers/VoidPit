package com.characterlim.voidpit;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticleAnimator {

    private final World world;
    private final VoidPitPlugin plugin;
    private static final int ITEM_DESTROY_PARTICLE_COUNT = 25;
    private static final int TPS = 20;
    private static final int ASCEND_ANIMATION_TICKS = TPS * 3;
    //private List<BukkitRunnable> tasks = null;

    public ParticleAnimator(World world, VoidPitPlugin instance) {
        this.world = world;
        this.plugin = instance;
    }

    public void animateItemDestroy(Location location) {
        this.world.spawnParticle(Particle.SMOKE_LARGE, location, ITEM_DESTROY_PARTICLE_COUNT, 0, 1, 0, 0);
    }

    public void animateAscend(Location startLocation) {

        Location pos = startLocation;
        Location endLocation = new Location(Config.Hologram.pos.getWorld(), Config.Hologram.pos.getX(), Config.Hologram.pos.getY(), Config.Hologram.pos.getZ());

        Vector dr = endLocation.subtract(startLocation).toVector().multiply(0.01 /*1.0 / ASCEND_ANIMATION_TICKS*/);
        final int[] tickCount = {0};

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                world.spawnParticle(Particle.END_ROD, pos, 1, 0, 0, 0, 0);
                pos.add(dr);
                tickCount[0] += 1;
                if(tickCount[0] >= ASCEND_ANIMATION_TICKS) this.cancel();
            }
        };

        plugin.getLogger().info(endLocation.toString());

        task.runTaskTimer(plugin, 1, 1);
    }
}
