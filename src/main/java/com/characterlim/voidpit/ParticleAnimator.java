package com.characterlim.voidpit;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static java.lang.Math.floor;

public class ParticleAnimator {

    private final World world;
    private final VoidPitPlugin plugin;
    private static final int ITEM_DESTROY_PARTICLE_COUNT = 25;
    private static final int TPS = 20;
    private static final int ASCEND_ANIMATION_TICKS = TPS * 3;

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

        Vector dr = endLocation.subtract(startLocation).toVector().multiply(1.0 / ASCEND_ANIMATION_TICKS);
        final int[] tickCount = {0};

        plugin.getLogger().info("start = " + pos);
        plugin.getLogger().info("dr = " + dr.toString());

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                world.spawnParticle(Particle.END_ROD, pos, 1, 0, 0, 0, 0);
                int dt = 1 + (int)floor(tickCount[0] / 10.0);
                tickCount[0] += dt;
                Vector change = new Vector(dr.getX(), dr.getY(), dr.getZ()).multiply(dt);
                pos.add(change);
                plugin.getLogger().info("spawning particle at " + pos);
                if(tickCount[0] >= ASCEND_ANIMATION_TICKS) this.cancel();
            }
        };

        task.runTaskTimer(plugin, 1, 1);
    }
}
